package rs.ac.bg.fon.springsocialnetwork.mapper;

import lombok.AllArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.dto.PostRequest;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.repository.TopicRepository;

import java.time.Instant;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
public class PostRequestMapper implements GenericMapper<PostRequest, Post> {

    private TopicRepository topicRepository;
    @Override
    public Post toEntity(PostRequest dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setTopic(topicRepository.getByName(dto.getTopicName()).orElseThrow(()->new MyRuntimeException("Topic with given name does not exist")));
        post.setCreatedDate(Instant.now());
        return post;
    }

    @Override
    public PostRequest toDto(Post entity) {
        return null;
    }
}
