package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.springsocialnetwork.dto.UserDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.mapper.UserMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Following;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.model.idclasses.FollowingId;
import rs.ac.bg.fon.springsocialnetwork.repository.FollowRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * @author UrosVesic
 */

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private FollowRepository followRepository;
    private UserMapper userMapper;


    @Transactional
    public void follow(User currentUser, Long idFollowed){
        Optional<User> userOptFollowed = userRepository.findById(idFollowed);

        User userFollowed = userOptFollowed.orElseThrow(() -> new MyRuntimeException("User not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userFollowed.getUsername().equals(username)){
            throw new MyRuntimeException("Following not allowed");
        }

        Following following = new Following(currentUser,userFollowed, Instant.now());
        Optional<Following> foll = followRepository.findById(new FollowingId(currentUser.getUserId(), idFollowed));
        if(foll.isPresent()){
            throw new MyRuntimeException("You already follow this user");
        }
        followRepository.save(following);
    }

    @Transactional
    public UserDto getUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        User user = userOpt.orElseThrow(()->new MyRuntimeException("User with id : "+id+" not found"));
        return userMapper.toDto(user);
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

    public List<UserDto> getAllFollowersForUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.orElseThrow(() -> new MyRuntimeException("User not found"));
        List<User> followers = user.getFollowers();
        return followers.stream().map((user1)->userMapper.toDto(user1)).collect(Collectors.toList());
    }
}
