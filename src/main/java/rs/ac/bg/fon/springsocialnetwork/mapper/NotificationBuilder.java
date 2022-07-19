package rs.ac.bg.fon.springsocialnetwork.mapper;

import lombok.AllArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.dto.ReactionDto;
import rs.ac.bg.fon.springsocialnetwork.model.Notification;
import rs.ac.bg.fon.springsocialnetwork.model.NotificationType;
import rs.ac.bg.fon.springsocialnetwork.model.Reaction;
import rs.ac.bg.fon.springsocialnetwork.model.ReactionType;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;

import java.time.Instant;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
public class NotificationBuilder {

    private AuthService authService;

    public Notification createNotificationForReaction(Reaction react){
        Notification not = new Notification();
        not.setFrom(react.getUser());
        not.setTo(react.getPost().getUser());
        not.setCreated_at(Instant.now());
        not.setPost(react.getPost());
        not.setRead(false);
        not.setNotificationType(setNotificationType(react.getReactionType()));
        return not;
    }

    private NotificationType setNotificationType(ReactionType reactionType) {
        if(reactionType==ReactionType.LIKE){
            return NotificationType.LIKE;
        }else{
            return NotificationType.DISLIKE;
        }
    }
}
