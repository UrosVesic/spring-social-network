package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.Comment;
import rs.ac.bg.fon.springsocialnetwork.model.MyEntity;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import java.util.List;

/**
 * @author UrosVesic
 */

public interface CommentRepository extends MyRepository, JpaRepository<Comment,Long> {
    List<Comment> findAllByPost(Post post);

    @Override
    default void deleteByParent(MyEntity parent) {
        deleteAllByPost((Post) parent);
    }

    public void deleteAllByPost(Post post);
    List<Comment> findByPost_idOrderByCreatedDateDesc(Long postId);
}
