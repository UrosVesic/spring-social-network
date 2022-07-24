package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.Message;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message,Long> {
    Optional<Message> findTopByFrom_usernameAndTo_usernameOrderByIdDesc(String from,String to);

    List<Message> findByTo_usernameAndFrom_username(String from, String to);

    List<Message> findByTo_usernameOrFrom_usernameOrderByIdDesc(String from,String to);

    int countByTo_usernameAndFrom_usernameAndSeenAt(String username, String with, Instant seenAt);

    Integer countByTo_usernameAndSeenAt(String username, Instant seenAt);

    List<Message> findByFrom_username(String username);

    List<Message> findByFrom_usernameAndSeenAt(String username,Instant seenAt);
}
