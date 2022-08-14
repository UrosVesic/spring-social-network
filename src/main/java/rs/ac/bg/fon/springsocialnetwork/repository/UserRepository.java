package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author UrosVesic
 */
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    List<User> findByUserIdNotIn(List<Long> following);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);
    List<User> findByUserIdNotInAndIsEnabled(List<Long> collect, boolean enabled);
}
