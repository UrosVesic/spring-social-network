package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;



    public void sendMessage() {
        messagingTemplate.convertAndSend("/topic", "Default message from our WS service");
    }

    public void sendMessage(final String payload) {
        messagingTemplate.convertAndSend("/topic", payload);
    }
}
