package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.model.Role;

/**
 * @author UrosVesic
 */
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
