package NULL.DTPomoziMi.web.DTO;

import java.sql.Date;
import java.sql.Time;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;

import NULL.DTPomoziMi.model.Location;
import NULL.DTPomoziMi.model.RequestStatus;
import NULL.DTPomoziMi.model.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "requests", itemRelation = "request")
public class RequestDTO extends RepresentationModel<RequestDTO> { // TODO validacija

	@Include
	private Long IdRequest;

	private String phone;

	private Date date;

	private Boolean executed;

	private String description;

	private Boolean recivedNotif;

	private RequestStatus status;

	private Time time;

	private User author;

	private User executor;

	private Location location;
}
