package rs.ac.bg.fon.springsocialnetwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.bg.fon.springsocialnetwork.mapper.PostRequestMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.PostResponseMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.TopicMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.UserMapper;
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
    public PostResponseMapper postMapper(CommentRepository commentRepository, ReactionRepository reactionRepository){
        return new PostResponseMapper(commentRepository,reactionRepository);
    }

    @Bean
    public TopicMapper topicMapper(PostRepository postRepository, AuthService authService){
        return new TopicMapper(postRepository,authService);
    }

    @Bean
    public PostRequestMapper PostRequestMapper(TopicRepository topicRepository){
        return new PostRequestMapper(topicRepository);
    }
}
