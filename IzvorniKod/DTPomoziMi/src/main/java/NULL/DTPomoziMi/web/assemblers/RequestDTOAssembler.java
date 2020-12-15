package NULL.DTPomoziMi.web.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.web.DTO.RequestDTO;
import NULL.DTPomoziMi.web.controller.RequestController;

@Component
public class RequestDTOAssembler extends RepresentationModelAssemblerSupport<Request, RequestDTO> {

	@Autowired
	private ModelMapper modelMapper;

	public RequestDTOAssembler() {
		super(RequestController.class, RequestDTO.class);
	}

	@Override
	public RequestDTO toModel(Request entity) {
		RequestDTO requestDTO = createModelWithId(entity.getIdRequest(), entity);
		modelMapper.map(entity, requestDTO);

		return requestDTO;
	}
}
