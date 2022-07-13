package rs.ac.bg.fon.springsocialnetwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.model.idclasses.FollowingId;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author UrosVesic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(FollowingId.class)
public class Following implements MyEntity{
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "following_user_id", nullable = false)
    private User following;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "followed_user_id", nullable = false)
    private User followed;
    private Instant followedAt;



}
