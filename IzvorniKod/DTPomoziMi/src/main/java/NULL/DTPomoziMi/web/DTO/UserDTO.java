package NULL.DTPomoziMi.web.DTO;

import NULL.DTPomoziMi.validation.MatchPassword;
import NULL.DTPomoziMi.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@MatchPassword(message = "{UserDTO.MatchPassword}")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserDTO extends RepresentationModel<UserDTO> {
    private Long id;

    @NotNull
    @Size(min=1, message = "{Size.UserDTO.firstName}")
    private String firstName;

    @NotNull
    @Size(message = "{Size.UserDTO.lastName}", min=1)
    private String lastName;

    @ValidPassword
    private String password;
    private String secondPassword;

    @NotNull
    @Email(message = "{UserDTO.email}")
    private String email;

    // TODO validacija duljine i sirine...
    private BigDecimal longitude;
    private BigDecimal latitude;

}
