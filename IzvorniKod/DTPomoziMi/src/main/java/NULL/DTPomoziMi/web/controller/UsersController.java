package NULL.DTPomoziMi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import NULL.DTPomoziMi.web.pagination.assemblers.UserDTOModelAssembler;

@RestController
@RequestMapping("/api")

public class UsersController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDTOModelAssembler userDTOModelAssembler;

    @PreAuthorize( value = "isAuthenticated()")
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            /*@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName*/
    @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, PagedResourcesAssembler<User> assembler){

        try{ //https://howtodoinjava.com/spring5/hateoas/pagination-links/
            Pageable paging = PageRequest.of(page, size);

            Page<User> pageUser;
            //if ime .. prezime ... else all
            pageUser = userService.findUsers(paging);

            //PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(pageUser.getSize(), pageUser.getNumber(), pageUser.getTotalElements(), pageUser.getTotalPages());

            PagedModel<UserDTO> pagedModel = assembler.toModel(pageUser, userDTOModelAssembler);

//            List<UserDTO> users = new ArrayList<>(); // da ne bude null
//            users = pageUser.getContent();

//            Map<String, Object> response = new HashMap<>();
//            response.put("users", response);
//            response.put("currentPage", pageUser.getNumber());
//            response.put("totalItems", pageUser.getTotalElements());
//            response.put("totalPages", pageUser.getTotalPages());

            return new ResponseEntity<>(pagedModel, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
