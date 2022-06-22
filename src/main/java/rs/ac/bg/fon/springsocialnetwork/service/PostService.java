package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.PostRequest;
import rs.ac.bg.fon.springsocialnetwork.dto.PostResponse;
import rs.ac.bg.fon.springsocialnetwork.mapper.PostRequestMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.PostResponseMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;

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
    private PostResponseMapper postResponseMapper;
    private PostRequestMapper postRequestMapper;

    public List<PostResponse> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        List<PostResponse> collect = posts.stream().map((post) -> postResponseMapper.toDto(post)).collect(Collectors.toList());
        Collections.sort(collect,(PostResponse p1,PostResponse p2)->p1.getLikeCount()>p2.getLikeCount()?-1:1);
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
}
