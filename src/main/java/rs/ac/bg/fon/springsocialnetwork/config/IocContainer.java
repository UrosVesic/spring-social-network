package rs.ac.bg.fon.springsocialnetwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.bg.fon.springsocialnetwork.mapper.*;
import rs.ac.bg.fon.springsocialnetwork.repository.*;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;

/**
 * @author UrosVesic
 */
@Configuration
public class IocContainer {
    @Bean
    public UserMapper userMapper(AuthService authService){
        return new UserMapper(authService);
    }

    @Bean
    public PostMapper postMapper(CommentRepository commentRepository, ReactionRepository reactionRepository, AuthService authService){
        return new PostMapper(commentRepository,reactionRepository,authService);
    }

    @Bean
    public TopicMapper topicMapper(PostRepository postRepository, AuthService authService){
        return new TopicMapper(postRepository,authService);
    }

    @Bean
    public PostRequestMapper PostRequestMapper(TopicRepository topicRepository){
        return new PostRequestMapper(topicRepository);
    }

    @Bean
    public CommentMapper commentMapper(PostRepository postRepository,AuthService authService){
        return new CommentMapper(postRepository,authService);
    }

    @Bean
    public ReactionMapper reactionMapper(PostRepository postRepository,AuthService authService){
        return new ReactionMapper(postRepository,authService);
    }

    @Bean
    public PostReportRequestMapper postReportRequestMapper(PostRepository postRepository,AuthService authService){
        return new PostReportRequestMapper(postRepository,authService);
    }

    @Bean
    public ReportedPostMapper reportedPostMapper(PostReportRepository reportRepository){
        return new ReportedPostMapper(reportRepository);
    }

    @Bean
    public ReportedUserMapper reportedUserMapper(PostReportRepository postReportRepository){
        return new ReportedUserMapper(postReportRepository);
    }
}
