package rs.ac.bg.fon.springsocialnetwork.mapper;

import rs.ac.bg.fon.springsocialnetwork.dto.UserDto;
import rs.ac.bg.fon.springsocialnetwork.model.User;

/**
 * @author UrosVesic
 */
public class UserMapper implements GenericMapper<UserDto, User>{

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
        return new UserDto(user.getUserId(),user.getUsername(),user.getEmail(),user.getCreated(),user.getFollowing().size(),user.getFollowers().size());
    }
}
