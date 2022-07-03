package rs.ac.bg.fon.springsocialnetwork.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;
import rs.ac.bg.fon.springsocialnetwork.dto.ReportedPostDto;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.model.PostReport;
import rs.ac.bg.fon.springsocialnetwork.model.ReportStatus;
import rs.ac.bg.fon.springsocialnetwork.repository.PostReportRepository;

import java.util.Optional;

/**
 * @author UrosVesic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedPostMapper implements GenericMapper<ReportedPostDto, Post> {

    private PostReportRepository reportRepository;
    @Override
    public Post toEntity(ReportedPostDto dto) {
        return null;
    }

    @Override
    public ReportedPostDto toDto(Post entity) {
       ReportedPostDto dto = new ReportedPostDto();
       dto.setId(entity.getId());
       dto.setContent(entity.getContent());
       dto.setTitle(entity.getTitle());
       PrettyTime p = new PrettyTime();
       dto.setDuraton(p.format(entity.getCreatedDate()));
       dto.setTopicname(entity.getTopic().getName());
       dto.setUsername(entity.getUser().getUsername());
       dto.setReportCount(getReportCount(entity));
       dto.setReportStatus(setReportStatus(entity));
        return dto;
    }

    private ReportStatus setReportStatus(Post post) {
        Optional<PostReport> report = reportRepository.findFirstByPost(post);
        return report.get().getReportStatus();
    }

    private int getReportCount(Post post) {
        return reportRepository.countByPost(post).intValue();
    }
}
