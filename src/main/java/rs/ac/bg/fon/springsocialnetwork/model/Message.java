package rs.ac.bg.fon.springsocialnetwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String content;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "from_user_id", nullable = false)
    User from;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "to_user_id", nullable = false)
    User to;
}
