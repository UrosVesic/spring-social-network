package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.PostReportRequest;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.mapper.PostReportRequestMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.model.PostReport;
import rs.ac.bg.fon.springsocialnetwork.model.ReportStatus;
import rs.ac.bg.fon.springsocialnetwork.repository.PostReportRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@Service
@AllArgsConstructor
public class PostReportService {

    private PostReportRepository postReportRepository;
    private PostRepository postRepository;
    PostReportRequestMapper mapper;
    @Transactional
    public void reportPost(PostReportRequest request){
        PostReport postReport = mapper.toEntity(request);
        List<PostReport> postReportsByPost = postReportRepository.findByPost_id(request.getPostId());
        if(postReportsByPost.stream().anyMatch(report->
                (report.getReportStatus().equals(ReportStatus.APPROVED)
                        ||report.getReportStatus().equals(ReportStatus.DELETED)))){
            throw new MyRuntimeException("This post is already reviewed and approved");
        }
        postReportRepository.save(postReport);
    }
    @Transactional
    public void changeReportStatus(ReportStatus reportStatus,Long postId) {
        List<PostReport> reports = postReportRepository.findByPost_id(postId);
        reports.forEach(r->{
            if(r.getReportStatus().equals(reportStatus)){
                throw new MyRuntimeException("Already has status: "+reportStatus);
            }
            if(reportStatus.equals(ReportStatus.APPROVED)){
                Post post = postRepository.findById(postId).orElseThrow((() -> new MyRuntimeException("Post not found")));
                post.setDeletebByAdmin(null);
                postRepository.save(post);
            }
            r.setReportStatus(reportStatus);
        });
        postReportRepository.saveAll(reports);
    }
}
