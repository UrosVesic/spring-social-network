package rs.ac.bg.fon.springsocialnetwork.mapper;

import lombok.AllArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.dto.TopicDto;
import rs.ac.bg.fon.springsocialnetwork.model.Topic;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;

@AllArgsConstructor
public class TopicMapper implements GenericMapper<TopicDto, Topic> {

    private PostRepository postRepository;

    @Override
    public Topic toEntity(TopicDto dto) {
        return null;
    }

    @Override
    public TopicDto toDto(Topic topic) {
        TopicDto dto = new TopicDto();
        dto.setDescription(topic.getDescription());
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setNumberOfPosts(postRepository.findByTopic(topic).size());
        return dto;
    }
}
