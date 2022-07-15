package rs.ac.bg.fon.springsocialnetwork.mapper;

import lombok.AllArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.dto.MessageDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.model.Message;
import rs.ac.bg.fon.springsocialnetwork.repository.UserRepository;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
public class MessageMapper implements GenericMapper<MessageDto, Message>{

    private UserRepository userRepository;

    @Override
    public Message toEntity(MessageDto dto) {
        Message message = new Message();
        message.setContent(dto.getContent());
        message.setFrom(userRepository.findByUsername(dto.getFrom()).orElseThrow(()->new MyRuntimeException("User not found")));
        message.setTo(userRepository.findByUsername(dto.getTo()).orElseThrow(()->new MyRuntimeException("User not found")));
        message.setSentAt(Instant.now());
        return message;
    }

    @Override
    public MessageDto toDto(Message entity) {
        MessageDto dto = new MessageDto();
        dto.setContent(entity.getContent());
        dto.setFrom(entity.getFrom().getUsername());
        dto.setTo(entity.getTo().getUsername());
        int minute = entity.getSentAt().atZone(ZoneOffset.UTC).getMinute();
        String min;
        if(minute<10){
            min = "0"+minute;
        }else{
            min = minute+"";
        }
        dto.setTime(entity.getSentAt().atZone(ZoneOffset.UTC).getHour()+":"+min);
        return dto;
    }
}
