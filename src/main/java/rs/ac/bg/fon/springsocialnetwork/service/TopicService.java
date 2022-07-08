package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.TopicDto;
import rs.ac.bg.fon.springsocialnetwork.mapper.TopicMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Topic;
import rs.ac.bg.fon.springsocialnetwork.repository.TopicRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@Service
@AllArgsConstructor
public class TopicService {
    private TopicRepository topicRepository;
    private TopicMapper topicMapper;

    @Transactional
    public List<TopicDto> getAllTopics(){
        List<Topic> topics = topicRepository.findAll();
        return topics.stream().map((topic)->topicMapper.toDto(topic)).collect(Collectors.toList());
    }

    @Transactional
    public void createTopic(TopicDto dto) {
        Topic topic = topicMapper.toEntity(dto);
        topicRepository.save(topic);
    }
}
