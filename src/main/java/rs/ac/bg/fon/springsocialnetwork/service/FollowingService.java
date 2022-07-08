package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.UserDto;
import rs.ac.bg.fon.springsocialnetwork.mapper.UserMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Following;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.repository.FollowRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@Service
@AllArgsConstructor
public class FollowingService {

    private FollowRepository followRepository;
    private UserMapper userMapper;

    @Transactional
    public List<UserDto> getFollowersForUser(Long userId) {
        List<Following> optFoll = followRepository.findAllByFollowed_userId(userId);
        List<User> followers = optFoll.stream().map(Following::getFollowing).collect(Collectors.toList());
        List<UserDto> followersDto= followers.stream().map((userToMap)->userMapper.toDto(userToMap)).collect(Collectors.toList());
        return followersDto;
    }
}
