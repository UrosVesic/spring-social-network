package rs.ac.bg.fon.springsocialnetwork.model.idclasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.model.User;

import javax.persistence.Embeddable;
import javax.persistence.IdClass;
import java.io.Serializable;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowingId implements Serializable {

    private Long following;
    private Long followed;

}
