package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.springsocialnetwork.dto.ReportedUserDto;
import rs.ac.bg.fon.springsocialnetwork.dto.UserDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.mapper.ReportedUserMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.UserMapper;
import rs.ac.bg.fon.springsocialnetwork.model.*;
import rs.ac.bg.fon.springsocialnetwork.model.idclasses.FollowingId;
import rs.ac.bg.fon.springsocialnetwork.repository.FollowRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.PostReportRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.RoleRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.UserRepository;

import java.time.Instant;
import java.util.*;
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
    private AuthService authService;
    private RoleRepository roleRepository;
    private PostReportRepository postReportRepository;
    private ReportedUserMapper reportedUserMapper;


    @Transactional
    public void follow(String username){
        User currentUser = authService.getCurrentUser();
        Optional<User> userOptFollowed = userRepository.findByUsername(username);

        User userFollowed = userOptFollowed.orElseThrow(() -> new MyRuntimeException("User not found"));

        if(userFollowed.getUsername().equals(currentUser.getUsername())){
            throw new MyRuntimeException("Following not allowed");
        }

        Following following = new Following(currentUser,userFollowed, Instant.now());
        followRepository.save(following);
    }

    @Transactional
    public void unfollow(User currentUser, String username){
        Optional<User> userOptFollowed = userRepository.findByUsername(username);

        User userFollowed = userOptFollowed.orElseThrow(() -> new MyRuntimeException("User not found"));

        if(userFollowed.getUsername().equals(currentUser.getUsername())){
            throw new MyRuntimeException("Following not allowed");
        }

        Following following = new Following(currentUser,userFollowed, Instant.now());
        followRepository.delete(following);
    }

    @Transactional
    public UserDto getUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        User user = userOpt.orElseThrow(()->new MyRuntimeException("User with id : "+id+" not found"));
        return userMapper.toDto(user);
    }
    @Transactional
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

    /*public List<UserDto> getAllFollowersForUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.orElseThrow(() -> new MyRuntimeException("User not found"));
        List<User> followers = user.getFollowers();
        return followers.stream().map((user1)->userMapper.toDto(user1)).collect(Collectors.toList());
    }*/
    @Transactional
    public List<UserDto> getAllFollowersForUser(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        User user = userOpt.orElseThrow(() -> new MyRuntimeException("User not found"));
        List<User> followers = user.getFollowers();
        return followers.stream().map((user1)->userMapper.toDto(user1)).collect(Collectors.toList());
    }
    @Transactional
    public List<UserDto> getAllFollowingForUser(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        User user = userOpt.orElseThrow(() -> new MyRuntimeException("User not found"));
        List<User> followers = user.getFollowing();
        return followers.stream().map((user1)->userMapper.toDto(user1)).collect(Collectors.toList());
    }

    @Transactional
    public UserDto getProfileInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new MyRuntimeException("User not found"));
        return userMapper.toDto(user);
    }
    @Transactional
    public List<UserDto> getAllSuggestedUsers() {
        List<User> notFollowing = userRepository.findByUserIdNotInAndIsEnabled(authService.getCurrentUser().getFollowing().stream().map((user)->user.getUserId()).collect(Collectors.toList()),true);
        if(notFollowing.size()==0){
            List<Long> myId=new ArrayList<>();
            myId.add(authService.getCurrentUser().getUserId());
            notFollowing=userRepository.findByUserIdNotIn(myId);
        }
        notFollowing.remove(authService.getCurrentUser());
        Collections.sort(notFollowing,(user1,user2)
                ->user2.getMutualFollowers(authService.getCurrentUser())-user1.getMutualFollowers(authService.getCurrentUser()));
        return notFollowing.stream().map((user)->userMapper.toDto(user)).collect(Collectors.toList());
    }
    @Transactional
    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(() -> new MyRuntimeException("User not found"));
        if(userRepository.findByUsername(userDto.getUsername()).isPresent() && !user.getUsername().equals(userDto.getUsername())){
            throw new MyRuntimeException("Username taken");
        }
        if(userRepository.findByEmail(userDto.getEmail()).isPresent() && !user.getEmail().equals(userDto.getEmail())){
            throw new MyRuntimeException("Email linked to another account");
        }
        user.setBio(userDto.getBio());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
    }

    public void assignRole(String username,String rolename) {
        Role role = roleRepository.findByName(rolename).orElseThrow(()->new MyRuntimeException(("Role not found")));
        User user = userRepository.findByUsername(username).orElseThrow(()->new MyRuntimeException("User not found"));
        user.addRole(role);
        userRepository.save(user);
    }

    @Transactional
    public List<ReportedUserDto> getReportedUsers() {
        List<PostReport> postReports =postReportRepository.findByReportStatus(ReportStatus.DELETED);
        List<User> users = postReports.stream().map(report->report.getPost().getUser()).collect(Collectors.toList());
        return users.stream().distinct().map(user->reportedUserMapper.toDto(user)).collect(Collectors.toList());
    }

    public void disableUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new MyRuntimeException("User not found"));
        user.setEnabled(false);
        userRepository.save(user);
    }

    public void enableUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new MyRuntimeException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }

}
