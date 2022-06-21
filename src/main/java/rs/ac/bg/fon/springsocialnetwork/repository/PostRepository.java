package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.model.Topic;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTopic(Topic topic);
}
