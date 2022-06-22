package rs.ac.bg.fon.springsocialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostRequest implements Dto{

    String title;
    String topicName;
    String content;

}
