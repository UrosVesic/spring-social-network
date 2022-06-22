package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.Topic;

import java.util.Optional;

/**
 * @author UrosVesic
 */
public interface TopicRepository extends JpaRepository<Topic,Long> {
    Optional<Topic> getByName(String topicName);
}
