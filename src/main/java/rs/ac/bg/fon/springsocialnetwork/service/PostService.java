package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.PostResponse;
import rs.ac.bg.fon.springsocialnetwork.mapper.PostMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;
    private PostMapper postMapper;

    public List<PostResponse> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return posts.stream().map((post) -> postMapper.toDto(post)).collect(Collectors.toList());
    }
}
