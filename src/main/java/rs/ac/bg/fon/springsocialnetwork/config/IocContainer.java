package rs.ac.bg.fon.springsocialnetwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.bg.fon.springsocialnetwork.mapper.PostMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.TopicMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.UserMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Topic;
import rs.ac.bg.fon.springsocialnetwork.repository.CommentRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.ReactionRepository;
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
    public PostMapper postMapper(CommentRepository commentRepository, ReactionRepository reactionRepository){
        return new PostMapper(commentRepository,reactionRepository);
    }

    @Bean
    public TopicMapper topicMapper(PostRepository postRepository){
        return new TopicMapper(postRepository);
    }
}
