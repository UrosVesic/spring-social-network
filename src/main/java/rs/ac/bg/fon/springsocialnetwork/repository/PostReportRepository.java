package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.model.PostReport;
import rs.ac.bg.fon.springsocialnetwork.model.ReportStatus;
import rs.ac.bg.fon.springsocialnetwork.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author UrosVesic
 */
public interface PostReportRepository extends JpaRepository<PostReport,Long> {
    Long countByPost(Post post);

    Optional<PostReport> findFirstByPost(Post post);

    List<PostReport> findAllByReportStatus(ReportStatus unsolved);

    List<PostReport> findAllByReportStatusIn(List<ReportStatus> statuses);

    List<PostReport> findByPost_id(Long postId);

    List<PostReport> findByReportStatus(ReportStatus status);



    List<PostReport> findByPost_userAndReportStatus(User user, ReportStatus status);
}
