package rs.ac.bg.fon.springsocialnetwork.mapper;

import lombok.AllArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.dto.TopicDto;
import rs.ac.bg.fon.springsocialnetwork.model.Topic;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;

import java.time.Instant;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
public class TopicMapper implements GenericMapper<TopicDto, Topic> {

    private PostRepository postRepository;
    private AuthService authService;

    @Override
    public Topic toEntity(TopicDto dto) {
        Topic topic = new Topic();
        topic.setDescription(dto.getDescription());
        topic.setName(dto.getName());
        topic.setCreatedDate(Instant.now());
        topic.setUser(authService.getCurrentUser());
        return topic;
    }

    @Override
    public TopicDto toDto(Topic topic) {
        TopicDto dto = new TopicDto();
        dto.setDescription(topic.getDescription());
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setNumberOfPosts(postRepository.findByTopicAndDeletebByAdminIsNull(topic).size());
        return dto;
    }
}
