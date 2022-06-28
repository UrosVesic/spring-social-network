package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author UrosVesic
 */
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    List<User> findByuserIdNotIn(List<Long> following);

}
