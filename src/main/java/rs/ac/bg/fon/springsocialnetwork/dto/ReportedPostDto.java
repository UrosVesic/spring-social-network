package rs.ac.bg.fon.springsocialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.model.ReportStatus;

/**
 * @author UrosVesic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedPostDto implements Dto{
    private Long id;
    private String title;
    private String content;
    private String username;
    private String topicname;
    private String duraton;
    private int reportCount;
    private ReportStatus reportStatus;
}
