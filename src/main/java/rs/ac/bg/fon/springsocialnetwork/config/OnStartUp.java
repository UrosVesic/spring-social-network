/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.springsocialnetwork.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.springsocialnetwork.dto.RegisterRequest;
import rs.ac.bg.fon.springsocialnetwork.model.Role;
import rs.ac.bg.fon.springsocialnetwork.repository.RoleRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.UserRepository;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;
import rs.ac.bg.fon.springsocialnetwork.service.RoleService;
import rs.ac.bg.fon.springsocialnetwork.service.UserService;

/**
 *
 * @author UrosVesic
 */
@Component
@AllArgsConstructor
public class OnStartUp {

    private RoleService roleService;
    private RoleRepository roleRepository;
    private AuthService authService;
    private UserService userService;
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        if (!roleRepository.findByName("USER").isPresent()) {
            roleService.addRole(new Role(null, "USER", "Social network registrated user"));
        }
        if (!roleRepository.findByName("ADMIN").isPresent()) {
            roleService.addRole(new Role(null, "ADMIN", "Social network administrator"));
        }
        if (!userRepository.findByUsername("uros99").isPresent()) {
            authService.signup(new RegisterRequest("uros@uros.com", "uros99", "uros99"));
            userService.assignRole("uros99", "ADMIN");
        }
    }

}
