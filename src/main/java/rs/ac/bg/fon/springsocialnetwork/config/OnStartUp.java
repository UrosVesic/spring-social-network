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
import rs.ac.bg.fon.springsocialnetwork.model.Message;
import rs.ac.bg.fon.springsocialnetwork.model.Role;
import rs.ac.bg.fon.springsocialnetwork.repository.MessageRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.RoleRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.UserRepository;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;
import rs.ac.bg.fon.springsocialnetwork.service.RoleService;
import rs.ac.bg.fon.springsocialnetwork.service.UserService;

import java.time.Instant;
import java.util.List;

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
        if (!roleRepository.existsByName("USER")) {
            roleService.addRole(new Role(null, "USER", "Social network registered user"));
        }
        if (!roleRepository.existsByName("ADMIN")) {
            roleService.addRole(new Role(null, "ADMIN", "Social network administrator"));
        }
        if (!userRepository.existsByUsername("uros99")) {
            authService.signup(new RegisterRequest("uros99uki@gmail.com", "uros99", "uros99"));
            userService.enableUser("uros99");
            userService.assignRole("uros99", "ADMIN");
        }

    }

}
