package rs.ac.bg.fon.springsocialnetwork.mapper;

import lombok.AllArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.dto.InboxMessageDto;
import rs.ac.bg.fon.springsocialnetwork.model.Message;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;

import java.time.ZoneId;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
public class InboxMessageMapper implements GenericMapper<InboxMessageDto, Message> {

    private AuthService authService;

    @Override
    public Message toEntity(InboxMessageDto dto) {
        return null;
    }

    @Override
    public InboxMessageDto toDto(Message entity) {
        InboxMessageDto dto = new InboxMessageDto();

        dto.setContent(setContent(entity.getContent()));
        int minute = entity.getSentAt().atZone(ZoneId.of("ECT",ZoneId.SHORT_IDS)).getMinute();
        String min;
        if(minute<10){
            min = "0"+minute;
        }else{
            min = minute+"";
        }
        dto.setTime(entity.getSentAt().atZone(ZoneId.of("ECT",ZoneId.SHORT_IDS)).getHour()+":"+min);
        return dto;
    }

    public InboxMessageDto toDto(Message entity, User currentUser){
        InboxMessageDto dto = toDto(entity);
        dto.setWith(setWith(entity,currentUser));
        return dto;
    }

    private String setContent(String content) {
        if(content.length()>15){
            String substring = content.substring(0, 15);
            String finalStr = substring.concat("...");
            return finalStr;
        }
        return content;
    }

    private String setWith(Message entity,User current) {
        if(entity.getFrom().getUsername()==current.getUsername()){
            return entity.getTo().getUsername();
        }
        return entity.getFrom().getUsername();
    }
}
