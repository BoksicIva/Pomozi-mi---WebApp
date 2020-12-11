package NULL.DTPomoziMi.web.DTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;

import NULL.DTPomoziMi.model.Location;
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
@Relation(collectionRelation = "candidacies", itemRelation = "candidacy")
public class CandidacyDTO extends RepresentationModel<CandidacyDTO> { // TODO validacija

	@Include
	private Long IdCandidacy;

	private Integer year;

	private Location location;
}
