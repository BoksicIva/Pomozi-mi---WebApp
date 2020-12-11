package NULL.DTPomoziMi.web.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;

import NULL.DTPomoziMi.model.Location;
import NULL.DTPomoziMi.validation.MatchPassword;
import NULL.DTPomoziMi.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@MatchPassword(message = "{UserDTO.MatchPassword}")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserDTO extends RepresentationModel<UserDTO> {
	@Include
	private Long IdUser;

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

    private Location location;

}
