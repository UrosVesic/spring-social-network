package rs.ac.bg.fon.springsocialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Dto{

    private Long userId;
    private String username;
    private String email;
    private Instant created;
    private int numOfFollowers;
    private int numOfFollowing;
}