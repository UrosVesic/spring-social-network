package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.InboxMessageDto;
import rs.ac.bg.fon.springsocialnetwork.dto.MessageDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.mapper.InboxMessageMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.MessageMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Message;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.repository.MessageRepository;

import javax.transaction.Transactional;
import java.time.Instant;
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
        messageRepository.saveAndFlush(messageMapper.toEntity(messageDto));
        webSocketService.sendMessage(id);
        webSocketService.sendNotificationToUser(id,"message");
    }

    public List<MessageDto> getAllMessages(){
        List<Message> all = messageRepository.findAll();
        return all.stream().map(m->messageMapper.toDto(m)).collect(Collectors.toList());
    }

    public MessageDto getLastMesage(String from, String to) {
        Optional<Message> optMessage = messageRepository.findTopByFrom_usernameAndTo_usernameOrderByIdDesc(from,to);
        Message message = optMessage.orElseThrow(() -> new MyRuntimeException("Message not found"));
        if(message.getSeenAt()==null){
            message.setSeenAt(Instant.now());
        }
        return messageMapper.toDto(message);
    }

    public List<MessageDto> getAllFromChat(String from, String to) {
        List<Message> messages1 = messageRepository.findByTo_usernameAndFrom_username(from,to);
        List<Message> messages2 = messageRepository.findByTo_usernameAndFrom_username(to,from);
        messages2.forEach(m->messages1.add(m));

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Message> notSeen = messages1.stream().filter(message -> message.getSeenAt() == null).collect(Collectors.toList());
        notSeen.stream().filter(m->!m.getFrom().getUsername().equals(currentUser)).forEach(m->m.setSeenAt(Instant.now()));
        messageRepository.saveAll(notSeen);


        messages1.sort(Comparator.comparing(Message::getSentAt));
        return messages1.stream().map(m->messageMapper.toDto(m)).collect(Collectors.toList());
    }

    public List<InboxMessageDto> inboxMessages() {
        User user = authService.getCurrentUser();
        String username = user.getUsername();
        List<Message> messagesOfCurrentUser = messageRepository.findByTo_usernameOrFrom_usernameOrderByIdDesc(username, username);
        List<InboxMessageDto> inbox = messagesOfCurrentUser.stream().map(msg -> inboxMessageMapper.toDto(msg,user)).collect(Collectors.toList());
        return inbox.stream().filter(distinctByKey(InboxMessageDto::getWith)).collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public Integer getNewMsgCount() {
        return messageRepository.countByTo_usernameAndSeenAt(authService.getCurrentUser().getUsername(), null);
    }

    public void readMessagesFrom(String username) {
        List<Message> messages = messageRepository.findByFrom_usernameAndSeenAt(username,null);
        messages.stream().forEach(m->m.setSeenAt(Instant.now()));
        messageRepository.saveAll(messages);
    }

    public void delete(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new MyRuntimeException("not found"));
        messageRepository.delete(message);

    }
}
