package rs.ac.bg.fon.springsocialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Dto{

    String from;
    String to;
    @NotEmpty(message = "Content is required")
    @NotNull(message = "Content is required")
    @NotBlank(message = "Content is required")
    String content;
    String time;
    boolean seen;

}
