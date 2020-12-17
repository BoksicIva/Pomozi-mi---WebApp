package NULL.DTPomoziMi.web.controller;

import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.service.RequestService;
import NULL.DTPomoziMi.web.DTO.RatingDTO;
import NULL.DTPomoziMi.web.DTO.RequestDTO;
import NULL.DTPomoziMi.web.assemblers.RatingDTOAssembler;
import NULL.DTPomoziMi.web.assemblers.RequestDTOAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import NULL.DTPomoziMi.web.assemblers.UserDTOModelAssembler;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserDTOModelAssembler userDTOModelAssembler;

    @Autowired
    private RequestDTOAssembler requestDTOAssembler;

    @Autowired
    private RatingDTOAssembler ratingDTOAssembler;

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

    @PreAuthorize( value = "isAuthenticated()")
    @GetMapping(value = "/{id}/myRequest", produces = { "application/json; charset=UTF-8" })
    public ResponseEntity<?> getmyRequests(@PathVariable("id") long userID,
                                        @PageableDefault Pageable pageable,
                                        PagedResourcesAssembler<Request> assembler){
        try{
            HashMap<String, List<Request>> l = requestService.getMyReq(userID); // mapa sa zahtjevima
            List<Request> l_active = l.get("aktivni"); //lista aktivnih
            List<Request> l_done = l.get("izvrseni"); // lista izvrsenih
            Page<Request> pageMyRequests = new PageImpl<Request>(l_active, pageable, l_active.size()); //treba vracat oba?
            PagedModel<RequestDTO> pagedModel = assembler.toModel(pageMyRequests, requestDTOAssembler);
            return new ResponseEntity<>(pagedModel, HttpStatus.OK);
        }catch(Exception e){
            logger.debug(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize( value = "isAuthenticated()")
    @GetMapping(value = "/{id}/rating", produces = { "application/json; charset=UTF-8" })
    public ResponseEntity<?> getRatings(@PathVariable("id") long userID, 
                                        @PageableDefault Pageable pageable,
                                        PagedResourcesAssembler<Rating> assembler){
        try{
            Set<Rating> ratings = userService.findRatings(userID);
            List<Rating> l = new ArrayList<Rating>(ratings);
            Page<Rating> pageRating = new PageImpl<Rating>(l, pageable, l.size());
            PagedModel<RatingDTO> pagedModel = assembler.toModel(pageRating, ratingDTOAssembler);
            return new ResponseEntity<>(pagedModel, HttpStatus.OK);
        }catch(Exception e){
            logger.debug(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured("ROLE_USER")
    @PostMapping(value = "", produces = { "application/json; charset=UTF-8" })
    public ResponseEntity<?> setRating(
            RatingDTO ratingDTO, BindingResult bindingResult, HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            logger.debug("Binding errors");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Rating rating = userService.createRating(ratingDTO);

        return ResponseEntity
                .created(URI.create("/api/rating/" + rating.getIdRating()))
                .body(ratingDTOAssembler.toModel(rating));
    }

}
