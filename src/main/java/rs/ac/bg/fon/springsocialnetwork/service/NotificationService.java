package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.NotificationDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.mapper.NotificationMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Notification;
import rs.ac.bg.fon.springsocialnetwork.repository.NotificationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
@Service
public class NotificationService {

    private WebSocketService webSocketService;
    private NotificationRepository notificationRepository;
    private NotificationMapper notificationMapper;
    private AuthService authService;

    public void save(Notification notification){
        notificationRepository.save(notification);
        webSocketService.sendNotificationToUser(notification.getTo().getUsername(),"notification");
    }



    public NotificationDto getLastNotificationForUser(String username) {
        Notification notification = notificationRepository.findTopByTo_usernameOrderByIdDesc(username).orElseThrow(() -> new MyRuntimeException("Notification not found"));
        return notificationMapper.toDto(notification);
    }

    public List<NotificationDto> getAllNotificationsForUser() {
        List<Notification> notifications = notificationRepository.findByTo_usernameOrderByIdDesc(authService.getCurrentUser().getUsername());
        return notifications.stream().map(n->notificationMapper.toDto(n)).collect(Collectors.toList());
    }

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new MyRuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);


    }
}
