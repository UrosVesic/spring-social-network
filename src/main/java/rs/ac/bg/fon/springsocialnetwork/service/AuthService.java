package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.AuthResponse;
import rs.ac.bg.fon.springsocialnetwork.dto.LoginRequest;
import rs.ac.bg.fon.springsocialnetwork.dto.RegisterRequest;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.jwt.JwtProvider;
import rs.ac.bg.fon.springsocialnetwork.model.Role;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.repository.RoleRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

/**
 * @author UrosVesic
 */
@Service
@AllArgsConstructor
public class AuthService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private RoleRepository roleRepository;

    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        Role role = roleRepository.findByName("USER");
        user.addRole(role);
        userRepository.save(user);
    }
    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = jwtProvider.generateToken(authenticate);
            return  new AuthResponse(token, loginRequest.getUsername(),isAdmin());
    }
    private String isAdmin() {
        User user = getCurrentUser();
        for (Role role:user.getRoles()){
            if(role.getName().equals("ADMIN"))
                return "yes";
        }
        return "no";
    }

    @Transactional
    public User getCurrentUser(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> currentUser = userRepository.findByUsername(username);
        return currentUser.orElseThrow(()->new MyRuntimeException("Current user not found"));
    }
}
