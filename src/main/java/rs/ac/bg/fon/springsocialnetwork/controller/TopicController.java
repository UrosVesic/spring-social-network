package rs.ac.bg.fon.springsocialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.springsocialnetwork.dto.TopicDto;
import rs.ac.bg.fon.springsocialnetwork.model.Topic;
import rs.ac.bg.fon.springsocialnetwork.service.TopicService;

import java.util.List;

/**
 * @author UrosVesic
 */
@RestController
@RequestMapping("/api/topic")
@AllArgsConstructor
public class TopicController {

    private TopicService topicService;

    @GetMapping("/all")
    public ResponseEntity<List<TopicDto>> getAllTopics(){
    return new ResponseEntity<>(topicService.getAllTopics(), HttpStatus.OK);
    }
}
