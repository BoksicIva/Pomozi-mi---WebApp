package NULL.DTPomoziMi.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import NULL.DTPomoziMi.web.assemblers.UserDTOModelAssembler;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDTOModelAssembler userDTOModelAssembler;

    @PreAuthorize( value = "isAuthenticated()")
    @GetMapping("")
    public ResponseEntity<?> getUsers(@PageableDefault Pageable pageable, PagedResourcesAssembler<User> assembler){

        try{
            Page<User> pageUser = userService.findUsers(pageable);

            PagedModel<UserDTO> pagedModel = assembler.toModel(pageUser, userDTOModelAssembler);

            return new ResponseEntity<>(pagedModel, HttpStatus.OK);
        }catch(Exception e){
            logger.debug(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize( value = "isAuthenticated()")
    @GetMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
    public ResponseEntity<?> getUser(@PathVariable("id") long userID){
        userService.getUserByID(userID);

        UserDTO user = userDTOModelAssembler.toModel(userService.getUserByID(userID));

        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }

}
