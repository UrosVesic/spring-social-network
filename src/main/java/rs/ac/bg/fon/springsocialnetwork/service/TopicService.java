package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.TopicDto;
import rs.ac.bg.fon.springsocialnetwork.mapper.TopicMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Topic;
import rs.ac.bg.fon.springsocialnetwork.repository.TopicRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TopicService {
    private TopicRepository topicRepository;
    private TopicMapper topicMapper;

    public List<TopicDto> getAllTopics(){
        List<Topic> topics = topicRepository.findAll();
        return topics.stream().map((topic)->topicMapper.toDto(topic)).collect(Collectors.toList());
    }
}
