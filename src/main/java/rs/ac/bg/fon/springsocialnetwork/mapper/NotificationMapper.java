package rs.ac.bg.fon.springsocialnetwork.mapper;

import rs.ac.bg.fon.springsocialnetwork.dto.NotificationDto;
import rs.ac.bg.fon.springsocialnetwork.model.Notification;
import rs.ac.bg.fon.springsocialnetwork.model.NotificationType;

/**
 * @author UrosVesic
 */
public class NotificationMapper implements GenericMapper<NotificationDto, Notification> {
    @Override
    public Notification toEntity(NotificationDto dto) {
        return null;
    }

    @Override
    public NotificationDto toDto(Notification entity) {
        NotificationDto dto = new NotificationDto();
        dto.setId(entity.getId());
        dto.setPostId(entity.getPost().getId());
        dto.setMessage(formatMessage(entity));
        dto.setRead(entity.isRead());
        return dto;
    }

    private String formatMessage(Notification entity) {
        if(entity.getNotificationType()== NotificationType.LIKE){
            return entity.getFrom().getUsername()+" liked your post";
        } else if (entity.getNotificationType() == NotificationType.DISLIKE) {
            return entity.getFrom().getUsername()+" disliked your post";
        }else{
            return entity.getFrom().getUsername()+" commented your post";
        }
    }
}
