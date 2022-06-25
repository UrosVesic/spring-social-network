package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.model.Topic;
import rs.ac.bg.fon.springsocialnetwork.model.User;

import java.util.List;

/**
 * @author UrosVesic
 */

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTopic(Topic topic);

    List<Post> findAllByUser_username(String username);


    List<Post> findByUser_userIdIn(List<Long> following);
}
