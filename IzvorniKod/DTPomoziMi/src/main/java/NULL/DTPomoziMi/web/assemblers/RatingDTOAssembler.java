package NULL.DTPomoziMi.web.assemblers;

import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.web.DTO.RatingDTO;
import NULL.DTPomoziMi.web.controller.UsersController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RatingDTOAssembler extends RepresentationModelAssemblerSupport<Rating, RatingDTO> {
	@Autowired
	private ModelMapper modelMapper;

	public RatingDTOAssembler() { super(UsersController.class, RatingDTO.class); }

	@Override
	public RatingDTO toModel(Rating entity) {
		RatingDTO ratingDTO = createModelWithId(entity.getIdRating(), entity);
		modelMapper.map(entity, ratingDTO);

		return ratingDTO;
	}
}
