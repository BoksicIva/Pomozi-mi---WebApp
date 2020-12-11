package NULL.DTPomoziMi.web.DTO;

import java.math.BigDecimal;

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
public class LocationDTO extends RepresentationModel<LocationDTO> { // TODO validacija
	@Include
	private Long IdLocation;

	private String adress;

	private String state;

	private BigDecimal longitude;

	private String town;

	private BigDecimal latitude;
}
