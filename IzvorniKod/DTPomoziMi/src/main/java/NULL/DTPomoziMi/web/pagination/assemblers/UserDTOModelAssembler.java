package NULL.DTPomoziMi.web.pagination.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import NULL.DTPomoziMi.web.controller.UsersController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class UserDTOModelAssembler extends RepresentationModelAssemblerSupport<User, UserDTO> {

	@Autowired
	private ModelMapper moddelMapper;
	
    public UserDTOModelAssembler(){
        super(UsersController.class, UserDTO.class);
    }

    @Override
    public CollectionModel<UserDTO> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserDTO> userDTOS = super.toCollectionModel(entities);

        Method method = null;
        try {
            method = UsersController.class.getMethod("getUsers", int.class, int.class, PagedResourcesAssembler.class);
        } catch (NoSuchMethodException e) { }

        userDTOS.add(linkTo(method, 0, 3, null).withSelfRel());
        return userDTOS;
    }

    @Override
    public UserDTO toModel(User entity) {

        UserDTO userDTO = instantiateModel(entity);

        //  TODO dodaj dohvat po id
        userDTO.add(linkTo(methodOn(UsersController.class).getUsers(0, 3, null)).withSelfRel());
 
        moddelMapper.map(entity, userDTO);
        
        return userDTO;
    }


}
