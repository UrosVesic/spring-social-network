package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.Following;
import rs.ac.bg.fon.springsocialnetwork.model.idclasses.FollowingId;

import java.util.List;
import java.util.Optional;

/**
 * @author UrosVesic
 */
public interface FollowRepository extends JpaRepository<Following, FollowingId> {

    List<Following> findAllByFollowed_userId(Long id);
}
