package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.springsocialnetwork.dto.UserDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.jwt.JwtProvider;
import rs.ac.bg.fon.springsocialnetwork.model.Following;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.model.idclasses.FollowingId;
import rs.ac.bg.fon.springsocialnetwork.repository.FollowRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private FollowRepository followRepository;

    @Transactional
    public void follow(Long idFollowing, Long idFollowed){
        Optional<User> userOptFollowing = userRepository.findById(idFollowing);
        Optional<User> userOptFollowed = userRepository.findById(idFollowed);

        User userFollowing = userOptFollowing.orElseThrow(() -> new MyRuntimeException("User not found"));
        User userFollowed = userOptFollowed.orElseThrow(() -> new MyRuntimeException("User not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!userFollowing.getUsername().equals(username)|| userFollowed.getUsername().equals(username)){
            throw new MyRuntimeException("Following not allowed");
        }

        Following following = new Following(userFollowing,userFollowed, Instant.now());
        Optional<Following> foll = followRepository.findById(new FollowingId(idFollowing, idFollowed));
        if(foll.isPresent()){
            throw new MyRuntimeException("You already follow this user");
        }
        followRepository.save(following);
    }

    @Transactional
    public UserDto getUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        User user = userOpt.orElseThrow(()->new MyRuntimeException("User with id : "+id+" not found"));
        return new UserDto(id,user.getUsername(),user.getEmail(),user.getCreated(),user.getFollowers().size(),user.getFollowing().size());
    }

    public void unfollow(Long idFollowing, Long idFollowed) {
        Optional<User> userOptFollowing = userRepository.findById(idFollowing);
        Optional<User> userOptFollowed = userRepository.findById(idFollowed);

        User userFollowing = userOptFollowing.orElseThrow(() -> new MyRuntimeException("User not found"));
        User userFollowed = userOptFollowed.orElseThrow(() -> new MyRuntimeException("User not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!userFollowing.getUsername().equals(username)|| userFollowed.getUsername().equals(username)){
            throw new MyRuntimeException("Unfollowing not allowed");
        }
        Optional<Following> follOpt = followRepository.findById(new FollowingId(idFollowing, idFollowed));
        Following foll = follOpt.orElseThrow(() -> new MyRuntimeException("You dont follow this user"));
        followRepository.delete(foll);
    }
}
