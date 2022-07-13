package rs.ac.bg.fon.springsocialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.springsocialnetwork.dto.MessageDto;
import rs.ac.bg.fon.springsocialnetwork.model.Message;
import rs.ac.bg.fon.springsocialnetwork.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;

    @PostMapping
    public ResponseEntity sendMessage(@RequestBody Message message){
        messageService.saveMessage(message);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MessageDto>> getAllMessages(){
        List<MessageDto> allMessages = messageService.getAllMessages();
        return new ResponseEntity<>(allMessages,HttpStatus.OK);
    }
}
