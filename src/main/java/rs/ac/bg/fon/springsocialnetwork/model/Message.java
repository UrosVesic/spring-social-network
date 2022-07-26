package rs.ac.bg.fon.springsocialnetwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements MyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Content is required")
    @NotNull(message = "Content is required")
    @NotBlank(message = "Content is required")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id", nullable = false)
    private User from;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id", nullable = false)
    private User to;
    private Instant sentAt;
    private Instant seenAt;
}
