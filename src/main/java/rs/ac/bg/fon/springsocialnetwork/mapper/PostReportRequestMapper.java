package rs.ac.bg.fon.springsocialnetwork.mapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.dto.PostReportRequest;
import rs.ac.bg.fon.springsocialnetwork.model.PostReport;
import rs.ac.bg.fon.springsocialnetwork.model.ReportStatus;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.UserRepository;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
@NoArgsConstructor
public class PostReportRequestMapper implements GenericMapper<PostReportRequest, PostReport>{

    private PostRepository postRepository;
    private AuthService authService;
    @Override
    public PostReport toEntity(PostReportRequest dto) {
        PostReport postReport = new PostReport();
        postReport.setPost(postRepository.getById(dto.getPostId()));
        postReport.setUser(authService.getCurrentUser());
        postReport.setReportStatus(ReportStatus.UNSOLVED);
        return postReport;
    }

    @Override
    public PostReportRequest toDto(PostReport entity) {
        return null;
    }
}
