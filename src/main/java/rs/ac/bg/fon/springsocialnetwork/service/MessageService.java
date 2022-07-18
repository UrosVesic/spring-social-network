package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.InboxMessageDto;
import rs.ac.bg.fon.springsocialnetwork.dto.MessageDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.mapper.InboxMessageMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.MessageMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Message;
import rs.ac.bg.fon.springsocialnetwork.repository.MessageRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private WebSocketService webSocketService;
    private MessageRepository messageRepository;
    private MessageMapper messageMapper;
    private InboxMessageMapper inboxMessageMapper;
    private AuthService authService;
    public void saveMessage(MessageDto messageDto,String id){
        messageRepository.save(messageMapper.toEntity(messageDto));
        //String suffix = "/"+messageDto.getFrom()+"/"+messageDto.getTo();
        webSocketService.sendMessage(id);
    }

    public List<MessageDto> getAllMessages(){
        List<Message> all = messageRepository.findAll();
        return all.stream().map(m->messageMapper.toDto(m)).collect(Collectors.toList());
    }

    public MessageDto getLastMesage(String from, String to) {
        Optional<Message> message = messageRepository.findTopByTo_usernameAndFrom_usernameOrderByIdDesc(from,to);
        return messageMapper.toDto(message.orElseThrow(()->new MyRuntimeException("Message not found")));
    }

    public List<MessageDto> getAllFromChat(String from, String to) {
        List<Message> messages1 = messageRepository.findByTo_usernameAndFrom_username(from,to);
        List<Message> messages2 = messageRepository.findByTo_usernameAndFrom_username(to,from);
        messages2.forEach(m->messages1.add(m));
        messages1.sort(Comparator.comparing(Message::getSentAt));
        return messages1.stream().map(m->messageMapper.toDto(m)).collect(Collectors.toList());
    }

    public List<InboxMessageDto> inboxMessages() {
        List<Message> messagesOfCurrentUser = messageRepository.findByTo_usernameOrFrom_usernameOrderByIdDesc(authService.getCurrentUser().getUsername(),authService.getCurrentUser().getUsername());
        List<InboxMessageDto> inbox = messagesOfCurrentUser.stream().map(msg -> inboxMessageMapper.toDto(msg)).collect(Collectors.toList());
        return inbox.stream().filter(distinctByKey(InboxMessageDto::getWith)).collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
