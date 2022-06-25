package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.model.Reaction;
import rs.ac.bg.fon.springsocialnetwork.model.ReactionType;
import rs.ac.bg.fon.springsocialnetwork.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author UrosVesic
 */

public interface ReactionRepository extends JpaRepository<Reaction,Long> {
    List<Reaction> findByPostAndReactionType(Post post, ReactionType like);

    Optional<Reaction> findByPost_idAndUser(Long postId, User currentUser);
}
