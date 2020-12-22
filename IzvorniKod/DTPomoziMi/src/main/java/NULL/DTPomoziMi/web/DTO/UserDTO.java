package NULL.DTPomoziMi.web.DTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;

import NULL.DTPomoziMi.model.Location;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserDTO extends RepresentationModel<UserDTO> {

	private Long IdUser;
	
	private String firstName;

	private String lastName;

	private String email;

	private Location location;
	
}
