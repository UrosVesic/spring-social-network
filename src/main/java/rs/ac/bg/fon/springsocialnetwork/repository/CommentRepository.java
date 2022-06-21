package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.Comment;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import java.util.List;

/**
 * @author UrosVesic
 */

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPost(Post post);
}
