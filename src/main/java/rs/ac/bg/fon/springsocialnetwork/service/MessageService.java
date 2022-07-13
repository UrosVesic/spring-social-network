package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.MessageDto;
import rs.ac.bg.fon.springsocialnetwork.model.Message;
import rs.ac.bg.fon.springsocialnetwork.repository.MessageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private WebSocketService webSocketService;
    private MessageRepository messageRepository;
    public void saveMessage(Message message){
        messageRepository.save(message);
        webSocketService.sendMessage();
    }

    public List<MessageDto> getAllMessages(){
        List<Message> all = messageRepository.findAll();
        return all.stream().map(m->{
            MessageDto dto = new MessageDto();
            dto.setContent(m.getContent());
            dto.setFrom(m.getFrom().getUsername());
            dto.setTo(m.getTo().getUsername());
            return dto;
        }).collect(Collectors.toList());
    }
}
