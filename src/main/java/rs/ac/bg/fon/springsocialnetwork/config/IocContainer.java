package rs.ac.bg.fon.springsocialnetwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.bg.fon.springsocialnetwork.mapper.*;
import rs.ac.bg.fon.springsocialnetwork.repository.*;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;
import rs.ac.bg.fon.springsocialnetwork.websockettest.UserHandshakeHandler;

/**
 * @author UrosVesic
 */
@Configuration
public class IocContainer {

    @Bean
    public NotificationBuilder notificationBuilder(AuthService authService){
        return new NotificationBuilder(authService);
    }

    @Bean
    public NotificationMapper notificationMapper(){
        return new NotificationMapper();
    }
    /*@Bean
    public InboxMessageMapper inboxMessageMapper(AuthService authService,MessageRepository messageRepository){
        return new InboxMessageMapper(authService,messageRepository);
    }*/
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

    @Bean
    public MessageMapper messageMapper(UserRepository userRepository){
        return new MessageMapper(userRepository);
    }
    @Bean
    public UserHandshakeHandler userHandshakeHandler(){
        return new UserHandshakeHandler();
    }
}
