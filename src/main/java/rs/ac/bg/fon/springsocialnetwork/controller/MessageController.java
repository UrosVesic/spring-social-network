package rs.ac.bg.fon.springsocialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.springsocialnetwork.dto.InboxMessageDto;
import rs.ac.bg.fon.springsocialnetwork.dto.MessageDto;
import rs.ac.bg.fon.springsocialnetwork.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;

    @PostMapping("/{id}")
    public ResponseEntity sendMessage(@PathVariable String id,@RequestBody MessageDto messageDto){
        messageService.saveMessage(messageDto,id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MessageDto>> getAllMessages(){
        List<MessageDto> allMessages = messageService.getAllMessages();
        return new ResponseEntity<>(allMessages,HttpStatus.OK);
    }

    @GetMapping("/last/{from}/{to}")
    public ResponseEntity<MessageDto> getLastMessage(@PathVariable String from, @PathVariable String to){
        MessageDto dto = messageService.getLastMesage(from,to);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/all/{from}/{to}")
    public ResponseEntity<List<MessageDto>> getAllFromChat(@PathVariable String from, @PathVariable String to){
        List<MessageDto> allFromChat = messageService.getAllFromChat(from, to);
        return new ResponseEntity<>(allFromChat,HttpStatus.OK);
    }

    @GetMapping("/inbox")
    public ResponseEntity<List<InboxMessageDto>> inboxMessages(){
        List<InboxMessageDto> inboxMessages = messageService.inboxMessages();
        return new ResponseEntity<>(inboxMessages,HttpStatus.OK);
    }
}
