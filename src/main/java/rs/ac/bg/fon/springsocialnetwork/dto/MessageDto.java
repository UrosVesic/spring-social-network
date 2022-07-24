package rs.ac.bg.fon.springsocialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Dto{

    String from;
    String to;
    String content;
    String time;
    boolean seen;

}
