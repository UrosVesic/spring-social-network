package rs.ac.bg.fon.springsocialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.springsocialnetwork.dto.AuthResponse;
import rs.ac.bg.fon.springsocialnetwork.dto.LoginRequest;
import rs.ac.bg.fon.springsocialnetwork.dto.RegisterRequest;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("Registration succesfull", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public  ResponseEntity<String> handleSQLIntegrityConstraintViolation(SQLIntegrityConstraintViolationException ex){
        return new ResponseEntity<>("Username or email already exists",HttpStatus.BAD_REQUEST);
    }


}
