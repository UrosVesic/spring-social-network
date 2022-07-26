package rs.ac.bg.fon.springsocialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.springsocialnetwork.dto.InboxMessageDto;
import rs.ac.bg.fon.springsocialnetwork.dto.MessageDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.service.MessageService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;

    @PostMapping("/{id}")
    public ResponseEntity sendMessage(@PathVariable String id,@RequestBody @Valid MessageDto messageDto){
        messageService.saveMessage(messageDto,id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/new-msg-count")
    public ResponseEntity<Integer> getNewMsgCount(){
        Integer newMsgCount = messageService.getNewMsgCount();
        return new ResponseEntity<>(newMsgCount,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MessageDto>> getAllMessages(){
        List<MessageDto> allMessages = messageService.getAllMessages();
        return new ResponseEntity<>(allMessages,HttpStatus.OK);
    }

    @PatchMapping("/read/{username}")
    public ResponseEntity readMessagesFrom(@PathVariable String username){
        messageService.readMessagesFrom(username);
        return new ResponseEntity(HttpStatus.OK);
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMessage(@PathVariable Long id){
        messageService.delete(id);
    }

    @ExceptionHandler(MyRuntimeException.class)
    public ResponseEntity handleMyRuntimeEx(){

        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getAllErrors();
        List<String> collect = allErrors.stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(collect,HttpStatus.BAD_REQUEST);
    }
}
