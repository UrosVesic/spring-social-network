package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.dto.NotificationDto;
import rs.ac.bg.fon.springsocialnetwork.model.Notification;

import java.util.List;
import java.util.Optional;

/**
 * @author UrosVesic
 */
public interface NotificationRepository extends JpaRepository<Notification,Long> {


    Optional<Notification> findTopByTo_usernameOrderByIdDesc(String username);

    List<Notification> findByTo_usernameOrderByIdDesc(String username);
}
