package rs.ac.bg.fon.springsocialnetwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
public class Topic implements MyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Topic name is required")
    @Column(unique = true)
    private String name;

    private String description;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
