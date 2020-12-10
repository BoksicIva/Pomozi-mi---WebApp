package NULL.DTPomoziMi.web.controller;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import NULL.DTPomoziMi.web.pagination.assemblers.UserDTOModelAssembler;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, PagedResourcesAssembler assembler){

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
