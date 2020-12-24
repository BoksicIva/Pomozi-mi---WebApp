package NULL.DTPomoziMi.web.controller;

import static NULL.DTPomoziMi.model.specification.UserSpecs.emailLike;
import static NULL.DTPomoziMi.model.specification.UserSpecs.firstNameLike;
import static NULL.DTPomoziMi.model.specification.UserSpecs.lastNameLike;
import static NULL.DTPomoziMi.model.specification.UserSpecs.phoneLike;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import NULL.DTPomoziMi.web.assemblers.UserDTOModelAssembler;

@RestController
@RequestMapping("/api/users")
@PreAuthorize(value = "isAuthenticated()")
public class UsersController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private UserDTOModelAssembler userDTOModelAssembler;

	/**
	 * Gets the users.
	 *
	 * @param pageable the pageable
	 * @param assembler the assembler
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 * @param phone the phone
	 * @param generalSearch the general search
	 * @return the users
	 */
	@GetMapping("")
	public ResponseEntity<?> getUsers(
		@PageableDefault Pageable pageable, PagedResourcesAssembler<User> assembler,
		@RequestParam(value = "firstName", required = false) String firstName,
		@RequestParam(value = "lastName", required = false) String lastName,
		@RequestParam(value = "email", required = false) String email,
		@RequestParam(value = "phone", required = false) String phone,
		@RequestParam(value = "generalSearch", required = false) String generalSearch
	) {
		try {
			Specification<User> specs = firstNameLike(firstName)
				.and(lastNameLike(lastName))
				.and(emailLike(email).and(phoneLike(phone)))
				.and(
					firstNameLike(generalSearch)
						.or(lastNameLike(generalSearch))
						.or(emailLike(generalSearch).or(phoneLike(generalSearch)))
				);

			Page<User> pageUser = userService.findUsers(pageable, specs);

			PagedModel<UserDTO> pagedModel = assembler.toModel(pageUser, userDTOModelAssembler);
			return new ResponseEntity<>(pagedModel, HttpStatus.OK);

		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	/**
	 * Gets the user.
	 *
	 * @param userID the user ID
	 * @return the user
	 */
	@GetMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> getUser(@PathVariable("id") long userID) {
		try {
			UserDTO user = userDTOModelAssembler.toModel(userService.getUserByID(userID));
			return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	/**
	 * Block user.
	 *
	 * @param IdUser the id user
	 * @return the response entity
	 */
	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/block/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> blockUser(@PathVariable(name = "id") long IdUser) {
		try {
			return ResponseEntity.ok(userDTOModelAssembler.toModel(userService.blockUser(IdUser)));
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	/**
	 * Gets the statistics.
	 *
	 * @param IdUser the id user
	 * @return the statistics
	 */
	@GetMapping(value = "/statistics/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> getStatistics(@PathVariable(name = "id") long IdUser) {
		try {
			EntityModel<?> entityModel = EntityModel.of(userService.getStatistics(IdUser));
			entityModel.add(linkTo(methodOn(getClass()).getStatistics(IdUser)).withSelfRel());
			return ResponseEntity.ok(entityModel);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

}
