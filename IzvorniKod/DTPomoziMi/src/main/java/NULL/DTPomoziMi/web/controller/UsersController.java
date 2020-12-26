package NULL.DTPomoziMi.web.controller;

import static NULL.DTPomoziMi.model.specification.UserSpecs.emailLike;
import static NULL.DTPomoziMi.model.specification.UserSpecs.firstNameLike;
import static NULL.DTPomoziMi.model.specification.UserSpecs.lastNameLike;
import static NULL.DTPomoziMi.model.specification.UserSpecs.phoneLike;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import NULL.DTPomoziMi.exception.BindingException;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.Common;
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
	 * @param pageable      the pageable
	 * @param assembler     the assembler
	 * @param firstName     the first name
	 * @param lastName      the last name
	 * @param email         the email
	 * @param phone         the phone
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
		@RequestParam(value = "generalSearch", required = false) String generalSearch,
		@AuthenticationPrincipal UserPrincipal principal
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

			Page<User> pageUser = userService.findUsers(pageable, specs, principal);

			PagedModel<UserDTO> pagedModel = assembler
				.toModel(
					pageUser, userDTOModelAssembler,
					linkTo(
						methodOn(UsersController.class)
							.getUsers(
								null, null, firstName, lastName, email, phone, generalSearch,
								principal
							)
					).withSelfRel()
				);
			pagedModel
				.add(
					linkGetUser(0), linkUpdateUser(0), linkBlockUser(0), linkGetStatistics(0),
					linkChainOfTrust(0)
				);
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
	public ResponseEntity<?> getUser(
		@PathVariable("id") long userID, @AuthenticationPrincipal UserPrincipal principal
	) {
		try {
			UserDTO user
				= userDTOModelAssembler.toModel(userService.getUserByID(userID, principal));
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

	@PutMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> updateUser(
		@PathVariable("id") long id, @RequestBody @Valid UserDTO userDTO,
		BindingResult bindingResult, @AuthenticationPrincipal UserPrincipal principal
	) {
		if (bindingResult.hasErrors()) hasErrors(bindingResult);

		try {
			return ResponseEntity
				.ok(userDTOModelAssembler.toModel(userService.updateUser(userDTO, id, principal)));
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	@GetMapping(value = "chainOfTrust/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> getChainOfTrust(
		@PathVariable long id, @AuthenticationPrincipal UserPrincipal principal
	) {
		try {
			EntityModel<?> model = EntityModel.of(userService.getChainOfTrust(id, principal));
			model.add(linkTo(methodOn(getClass()).getChainOfTrust(id, principal)).withSelfRel());
			return ResponseEntity.ok(model);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	private void hasErrors(BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		String errors = Common.stringifyErrors(fieldErrors);
		logger.debug("Binding field errors {}", errors);

		throw new BindingException(errors, fieldErrors);
	}

	private Link affordanceGetUsers() {
		return linkTo(methodOn(getClass()).getUsers(null, null, null, null, null, null, null, null))
			.withRel("users")
			.withType("get");
	}

	private Link linkGetUser(long id) {
		return linkTo(methodOn(getClass()).getUser(id, null)).withRel("one").withType("get");
	}

	private Link linkBlockUser(long id) {
		return linkTo(methodOn(getClass()).blockUser(id)).withRel("block").withType("post");
	}

	private Link linkGetStatistics(long id) {
		return linkTo(methodOn(getClass()).getStatistics(id)).withRel("statistics").withType("get");
	}

	private Link linkUpdateUser(long id) {
		return linkTo(methodOn(getClass()).updateUser(id, null, null, null))
			.withRel("update")
			.withType("put");
	}

	private Link linkChainOfTrust(long id) {
		return linkTo(methodOn(getClass()).getChainOfTrust(id, null))
			.withRel("chainOfTrust")
			.withType("get");
	}

}
