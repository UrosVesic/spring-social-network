package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.model.Role;
import rs.ac.bg.fon.springsocialnetwork.repository.RoleRepository;

/**
 * @author UrosVesic
 */
@Service
@AllArgsConstructor
public class RoleService {
    private RoleRepository roleRepository;
    public void addRole(Role role) {
        roleRepository.save(role);
    }
}
