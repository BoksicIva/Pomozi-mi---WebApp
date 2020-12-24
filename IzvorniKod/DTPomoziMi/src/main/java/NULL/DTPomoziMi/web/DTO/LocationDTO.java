package NULL.DTPomoziMi.web.DTO;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@Relation(collectionRelation = "locations", itemRelation = "location")
public class LocationDTO extends RepresentationModel<LocationDTO> {
	@Include
	private Long IdLocation;

	@NotNull
	private String adress;

	@NotNull
	private String state;

	@NotNull
	private BigDecimal longitude;

	@NotNull
	private String town;

	@NotNull
	private BigDecimal latitude;
}
