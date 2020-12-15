package NULL.DTPomoziMi.web.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import NULL.DTPomoziMi.web.controller.UsersController;

@Component
public class UserDTOModelAssembler extends RepresentationModelAssemblerSupport<User, UserDTO> {
	@Autowired
	private ModelMapper modelMapper;

	public UserDTOModelAssembler() {
		super(UsersController.class, UserDTO.class);
	}

	@Override
	public UserDTO toModel(User entity) {
		UserDTO userDTO = createModelWithId(entity.getIdUser(), entity);
		modelMapper.map(entity, userDTO);

		return userDTO;
	}

}
