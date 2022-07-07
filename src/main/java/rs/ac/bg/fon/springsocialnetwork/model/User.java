package rs.ac.bg.fon.springsocialnetwork.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "myuser")
public class User implements MyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotBlank(message = "Email is required")
    private String email;
    private Instant created;
    private String bio;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "following",
            joinColumns = @JoinColumn(name = "following_user_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_user_id"))
    private List<User> following;

    @ManyToMany(mappedBy = "following",fetch = FetchType.LAZY)
    private List<User> followers;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles"
            ,joinColumns = {@JoinColumn(name = "userId")}
            ,inverseJoinColumns = {@JoinColumn(name="roleId")})
    private Set<Role> roles;
    public int getMutualFollowers(User currentUser) {
        List<User> listOfMutualFoll = followers.stream()
                .filter(two -> currentUser.getFollowers().stream().anyMatch(one -> one.getUsername().equals(two.getUsername()))).collect(Collectors.toList());
        return listOfMutualFoll.size();
    }
}
