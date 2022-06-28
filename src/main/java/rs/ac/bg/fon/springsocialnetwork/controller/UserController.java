package rs.ac.bg.fon.springsocialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.springsocialnetwork.dto.UserDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
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

    @PostMapping(value = "/follow/{username}")
    public ResponseEntity<String> follow(@PathVariable String username){
        userService.follow(authService.getCurrentUser(), username);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @PostMapping("/unfollow/{username}")
    public ResponseEntity unfollow(@PathVariable String username){
        userService.unfollow(authService.getCurrentUser(), username);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }
    @DeleteMapping("/{idFollowing}/unfollow/{idFollowed}")
    public ResponseEntity unfollow(@PathVariable Long idFollowing,@PathVariable Long idFollowed){
        userService.unfollow(idFollowing,idFollowed);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        UserDto userDto = userService.getUser(id);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

   /* @GetMapping("/followers/{userId}")
    public ResponseEntity<List<UserDto>> getAllFollowersForUser(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getAllFollowersForUser(userId),HttpStatus.OK);
    }*/

    @GetMapping("/profile-info/{username}")
    public ResponseEntity<UserDto> getProfileInfo(@PathVariable String username){
        UserDto userResponse=userService.getProfileInfo(username);
        return new ResponseEntity<UserDto>(userResponse,HttpStatus.OK);
    }
    @GetMapping("/suggested")
    public ResponseEntity<List<UserDto>> getAllSuggestedUsers(){
        List<UserDto> users = userService.getAllSuggestedUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<List<UserDto>> getAllFollowersForUser(@PathVariable String username){
        return new ResponseEntity<>(userService.getAllFollowersForUser(username),HttpStatus.OK);
    }

    @GetMapping("/following/{username}")
    public ResponseEntity<List<UserDto>> getAllFollowingForUser(@PathVariable String username){
        return new ResponseEntity<>(userService.getAllFollowingForUser(username),HttpStatus.OK);
    }

    @ExceptionHandler(MyRuntimeException.class)
    public  ResponseEntity<String> handleMyRuntimeException(MyRuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
