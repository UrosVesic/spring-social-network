package rs.ac.bg.fon.springsocialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.springsocialnetwork.dto.UserDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;
import rs.ac.bg.fon.springsocialnetwork.service.UserService;

import java.util.List;

/**
 * @author UrosVesic
 */
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private AuthService authService;

    @PostMapping("/follow/{idFollowed}")
    public ResponseEntity<String> follow(@PathVariable Long idFollowed){
        userService.follow(authService.getCurrentUser(), idFollowed);
        return new ResponseEntity<>("Follow succesful", HttpStatus.CREATED);
    }
    @DeleteMapping("/{idFollowing}/unfollow/{idFollowed}")
    public ResponseEntity<String> unfollow(@PathVariable Long idFollowing,@PathVariable Long idFollowed){
        userService.unfollow(idFollowing,idFollowed);
        return new ResponseEntity<>("Unfollow succesful", HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        UserDto userDto = userService.getUser(id);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<UserDto>> getAllFollowersForUser(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getAllFollowersForUser(userId),HttpStatus.OK);
    }

    @ExceptionHandler(MyRuntimeException.class)
    public  ResponseEntity<String> handleMyRuntimeException(MyRuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
