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

/**
 * @author UrosVesic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "following",
            joinColumns = @JoinColumn(name = "following_user_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_user_id"))
    private List<User> following;

    @ManyToMany(mappedBy = "following",fetch = FetchType.LAZY)
    private List<User> followers;

}
