package rs.ac.bg.fon.springsocialnetwork.mapper;

import lombok.AllArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.dto.UserDto;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
public class UserMapper implements GenericMapper<UserDto, User> {

    private AuthService authService;

    @Override
    public User toEntity(UserDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setCreated(dto.getCreated());
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setCreated(user.getCreated());
        dto.setEmail(user.getEmail());
        dto.setFollowedByCurrentUser(user.getFollowers().contains(authService.getCurrentUser()));
        dto.setNumOfFollowers(user.getFollowers().size());
        dto.setNumOfFollowing(user.getFollowing().size());
        dto.setMutualFollowers(user.getMutualFollowers(authService.getCurrentUser()));
        dto.setBio(user.getBio());
        return dto;
    }
}
