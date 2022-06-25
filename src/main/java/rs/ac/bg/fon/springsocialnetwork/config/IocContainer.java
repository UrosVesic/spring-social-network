package rs.ac.bg.fon.springsocialnetwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.bg.fon.springsocialnetwork.mapper.*;
import rs.ac.bg.fon.springsocialnetwork.repository.CommentRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.ReactionRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.TopicRepository;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;

/**
 * @author UrosVesic
 */
@Configuration
public class IocContainer {
    @Bean
    public UserMapper userMapper(){
        return new UserMapper();
    }

    @Bean
    public PostResponseMapper postMapper(CommentRepository commentRepository, ReactionRepository reactionRepository, AuthService authService){
        return new PostResponseMapper(commentRepository,reactionRepository,authService);
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
}
