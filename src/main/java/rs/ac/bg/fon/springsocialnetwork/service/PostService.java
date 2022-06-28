package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.PostRequest;
import rs.ac.bg.fon.springsocialnetwork.dto.PostResponse;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.mapper.PostRequestMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.PostMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.TopicRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
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


    public List<PostResponse> getAllPostsForUser(String username) {
        List<Post> posts = postRepository.findAllByUser_username(username);
        return posts.stream().map((post)->postResponseMapper.toDto(post)).collect(Collectors.toList());
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<PostResponse> getAllPostsForFollowingUsers() {
        User currentUser = authService.getCurrentUser();
        List<Post> posts = postRepository.findByUser_userIdIn( currentUser.getFollowing().stream().map((user)->user.getUserId()).collect(Collectors.toList()));
        return posts.stream().map((post)->postResponseMapper.toDto(post)).collect(Collectors.toList());
    }

    public void updatePost(Long id,PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(()->new MyRuntimeException("Post not found"));
        post.setTopic(topicRepository.getByName(postRequest.getTopicName()).orElseThrow(()->new MyRuntimeException("Topic not found")));
        post.setContent(postRequest.getContent());
        post.setTitle(postRequest.getTitle());
        postRepository.save(post);
    }
}
