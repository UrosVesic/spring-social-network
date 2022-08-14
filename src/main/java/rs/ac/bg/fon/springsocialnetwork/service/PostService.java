package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
import rs.ac.bg.fon.springsocialnetwork.repository.MyRepository;
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
    private ApplicationContext context;
    @Transactional
    public List<PostResponse> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        List<PostResponse> collect = posts.stream().map((post) -> postResponseMapper.toDto(post)).collect(Collectors.toList());
        Collections.sort(collect,(p1,p2)->p1.getLikes()-p1.getDislikes());
        return collect;

    }

    @Transactional
    public PostResponse createPost(PostRequest postRequest, User currentUser) {
        Post post = postRequestMapper.toEntity(postRequest);
        post.setUser(currentUser);
        postRepository.save(post);
        return postResponseMapper.toDto(post);
    }
    @Transactional
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new MyRuntimeException("Post not found"));
        return postResponseMapper.toDto(post);
    }

    @Transactional
    public List<PostResponse> getAllPostsForUser(String username) {
        List<Post> posts = postRepository.findAllByUser_usernameAndDeletebByAdminIsNull(username);
        return posts.stream().map((post)->postResponseMapper.toDto(post)).collect(Collectors.toList());
    }
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new MyRuntimeException("Post not found"));
        if(!post.getUser().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new MyRuntimeException("You are not owner of the post");
        }
        post.returnChildRepositories(context).forEach(r->r.deleteByParent(post));
        postRepository.deleteById(id);
    }

    @Transactional
    public List<PostResponse> getAllPostsForFollowingUsers() {
        User currentUser = authService.getCurrentUser();
        List<Post> posts = postRepository.findByUser_userIdInAndDeletebByAdminIsNull( currentUser.getFollowing().stream().map(User::getUserId).collect(Collectors.toList()));
        posts = posts.stream().sorted(Comparator.comparing(Post::getCreatedDate).reversed()).collect(Collectors.toList());
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
        return collect.stream().sorted(Comparator.comparing(ReportedPostDto::getReportCount)).collect(Collectors.toCollection(LinkedHashSet::new));
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

    public List<PostResponse> getAllPostsForTopic(String topicName) {
        List<Post> posts = postRepository.findByTopic_name(topicName);
        return posts.stream()
                .map(p -> postResponseMapper.toDto(p))
                .sorted((p1,p2)->likeDislikeDifference(p2)-likeDislikeDifference(p1))
                .collect(Collectors.toList());
    }

    public int likeDislikeDifference(PostResponse p){
        return p.getLikes()-p.getDislikes();
    }
}
