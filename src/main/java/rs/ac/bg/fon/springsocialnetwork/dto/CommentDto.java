package rs.ac.bg.fon.springsocialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto implements Dto{

    private Long id;
    private Long postId;
    private String text;
    private String username;
    private String duration;

}
