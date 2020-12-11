package NULL.DTPomoziMi.web.DTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "requests", itemRelation = "request")
public class RequestDTO extends RepresentationModel<RequestDTO> {

	private Long id;

	@NotNull
	@Size(min = 1, message = "{Size.RequestDTO.opis}")
	private String description;

	@NotNull
	@Size(message = "{Size.RequestDTO.status}")
	private String status;

	@Size(message = "{Size.RequestDTO.date}")
	private Date date;

	@Size(message = "{Size.RequestDTO.time}")
	private Time time;

	@Size(message = "{Size.RequestDTO.phone}", min = 9)
	private String phone;

	@NotNull
	@Size(message = "{Size.RequestDTO.id_Author}")
	private Long id_Author;

	@Size(message = "{Size.RequestDTO.id_executor}")
	private Long id_executor;

	private BigDecimal longitude;
	private BigDecimal latitude;

}
