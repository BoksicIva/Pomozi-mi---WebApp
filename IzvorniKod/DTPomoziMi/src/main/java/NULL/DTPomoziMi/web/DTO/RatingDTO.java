package NULL.DTPomoziMi.web.DTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;

import NULL.DTPomoziMi.model.Request;
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
@Relation(collectionRelation = "ratings", itemRelation = "rating")
public class RatingDTO extends RepresentationModel<RatingDTO> { // TODO validacija
	@Include
	private Long IdRating;

	private String comment;

	private Integer rate;

	private User rated;

	private User rator;

	private Request request;

}
