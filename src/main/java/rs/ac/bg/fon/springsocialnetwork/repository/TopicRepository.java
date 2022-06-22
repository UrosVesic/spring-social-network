package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.Topic;

/**
 * @author UrosVesic
 */
public interface TopicRepository extends JpaRepository<Topic,Long> {
}