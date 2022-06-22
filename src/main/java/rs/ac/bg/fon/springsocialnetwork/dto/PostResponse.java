package rs.ac.bg.fon.springsocialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author UrosVesic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements Dto{

    private Long id;
    private String title;
    private String content;
    private String userName;
    private String topicName;
    private Integer likes;
    private Integer dislikes;
    private Integer commentCount;
    private String duration;
    private Integer likeCount;
}
