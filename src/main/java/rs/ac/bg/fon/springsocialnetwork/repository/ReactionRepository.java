package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.*;

import java.util.List;
import java.util.Optional;

/**
 * @author UrosVesic
 */

public interface ReactionRepository extends JpaRepository<Reaction,Long>,MyRepository {

    @Override
    default void deleteByParent(MyEntity parent) {
        deleteAllByPost((Post) parent);
    }

    public void deleteAllByPost(Post post);

    List<Reaction> findByPostAndReactionType(Post post, ReactionType like);

    Optional<Reaction> findByPost_idAndUser(Long postId, User currentUser);
}
