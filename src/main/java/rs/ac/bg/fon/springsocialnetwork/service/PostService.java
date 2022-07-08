package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.PostRequest;
import rs.ac.bg.fon.springsocialnetwork.dto.PostResponse;
import rs.ac.bg.fon.springsocialnetwork.dto.ReportedPostDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.mapper.PostRequestMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.PostMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.ReportedPostMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.model.PostReport;
import rs.ac.bg.fon.springsocialnetwork.model.ReportStatus;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.repository.PostReportRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.TopicRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;
    private PostMapper postResponseMapper;
    private PostRequestMapper postRequestMapper;
    private AuthService authService;
    private TopicRepository topicRepository;
    private PostReportRepository postReportRepository;
    private ReportedPostMapper reportedPostMapper;

    public List<PostResponse> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        List<PostResponse> collect = posts.stream().map((post) -> postResponseMapper.toDto(post)).collect(Collectors.toList());
        Collections.sort(collect,(PostResponse p1,PostResponse p2)->p1.getLikes()-p1.getDislikes()>p2.getLikes()-p2.getDislikes()?-1:1);
        return collect;

    }

    @Transactional
    public PostResponse createPost(PostRequest postRequest, User currentUser) {
        Post post = postRequestMapper.toEntity(postRequest);
        post.setUser(currentUser);
        postRepository.save(post);
        return postResponseMapper.toDto(post);
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.getById(id);
        return postResponseMapper.toDto(post);
    }

    @Transactional
    public List<PostResponse> getAllPostsForUser(String username) {
        List<Post> posts = postRepository.findAllByUser_usernameAndDeletebByAdminIsNull(username);
        return posts.stream().map((post)->postResponseMapper.toDto(post)).collect(Collectors.toList());
    }
    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<PostResponse> getAllPostsForFollowingUsers() {
        User currentUser = authService.getCurrentUser();
        List<Post> posts = postRepository.findByUser_userIdInAndDeletebByAdminIsNull( currentUser.getFollowing().stream().map(User::getUserId).collect(Collectors.toList()));
        return posts.stream().map((post)->postResponseMapper.toDto(post)).collect(Collectors.toList());
    }
    @Transactional
    public void updatePost(Long id,PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(()->new MyRuntimeException("Post not found"));
        post.setTopic(topicRepository.getByName(postRequest.getTopicName()).orElseThrow(()->new MyRuntimeException("Topic not found")));
        post.setContent(postRequest.getContent());
        post.setTitle(postRequest.getTitle());
        postRepository.save(post);
    }
    @Transactional
    public Set<ReportedPostDto> getAllUnsolvedReportedPosts() {
        List<PostReport> postReports = postReportRepository.findAllByReportStatus(ReportStatus.UNSOLVED);
        Set<Post> reportedPosts;
        reportedPosts = postReports.stream().map(PostReport::getPost).collect(Collectors.toSet());
        Set<ReportedPostDto> collect = reportedPosts.stream().map(post -> reportedPostMapper.toDto(post)).collect(Collectors.toSet());
        return collect.stream().sorted((report1,report2)->report1.getReportCount()>report2.getReportCount()?-1:1).collect(Collectors.toCollection(LinkedHashSet::new));
    }
    @Transactional
    public Set<ReportedPostDto> getAllSolvedReportedPosts() {
        List<ReportStatus> statuses = new ArrayList<>();
        statuses.add(ReportStatus.APPROVED);
        statuses.add(ReportStatus.DELETED);
        List<PostReport> postReports = postReportRepository.findAllByReportStatusIn(statuses);
        Set<Post> reportedPosts;
        reportedPosts = postReports.stream().map(PostReport::getPost).collect(Collectors.toSet());
        return reportedPosts.stream().map(post -> reportedPostMapper.toDto(post)).collect(Collectors.toSet());
    }
    @Transactional
    public void softDeletePost(Long id) {
        Optional<Post> optPost = postRepository.findById(id);
        Post post = optPost.orElseThrow(()->new MyRuntimeException("Post not found"));
        post.setDeletebByAdmin(Instant.now());
        postRepository.save(post);
        List<PostReport> postReports = postReportRepository.findByPost_id(id);
        postReports.forEach(report->report.setReportStatus(ReportStatus.DELETED));
        //postReports = postReports.stream().peek(report->report.setReportStatus(ReportStatus.DELETED)).collect(Collectors.toList());
        postReportRepository.saveAll(postReports);
    }
}
