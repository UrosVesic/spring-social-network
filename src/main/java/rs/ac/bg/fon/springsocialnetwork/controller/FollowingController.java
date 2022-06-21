package rs.ac.bg.fon.springsocialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.springsocialnetwork.dto.UserDto;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.service.FollowingService;

import java.util.List;

/**
 * @author UrosVesic
 */
@RestController
@RequestMapping("/api/following")
@AllArgsConstructor
public class FollowingController {

    private FollowingService followingService;

    @GetMapping("/followers/{userId}")
    public List<UserDto> getFollowersForUser(@PathVariable Long userId){
        return followingService.getFollowersForUser(userId);

    }
}
