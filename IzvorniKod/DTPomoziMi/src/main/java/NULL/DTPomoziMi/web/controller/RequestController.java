package NULL.DTPomoziMi.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import NULL.DTPomoziMi.model.Location;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.web.DTO.UserRegisterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.service.RequestService;
import NULL.DTPomoziMi.web.DTO.RequestDTO;
import NULL.DTPomoziMi.web.assemblers.RequestDTOAssembler;

@PreAuthorize(value = "isAuthenticated()")
@RestController
@RequestMapping("/api/requests")
public class RequestController { // TODO linkovi...
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RequestService requestService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private RequestDTOAssembler requestDTOassembler;

	/**
	 * Returns active requests, based on the radius and logged in User's location.
	 * The radius is optional and if not set, 0 is treated as default value. If the
	 * logged in user hasn't set his/her location then only requests without
	 * location are returned.
	 */
	@GetMapping(value = "/active", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> getAll(
		@PageableDefault Pageable pageable, PagedResourcesAssembler<Request> assembler,
		@RequestParam(name = "radius", required = false) Double radius
	) {

		Page<Request> page = requestService.findAll(pageable);

		PagedModel<RequestDTO> pagedModel
			= assembler
				.toModel(
					page, requestDTOassembler,
					linkTo(methodOn(RequestController.class).getAll(null, null,null)).withSelfRel()
				);

		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<RequestDTO> getRequest(@PathVariable("id") long id) {

		RequestDTO req = requestDTOassembler.toModel(requestService.getRequestbyId(id));

		return new ResponseEntity<RequestDTO>(req, HttpStatus.OK);
	}

	@Secured("ROLE_USER")
	@PostMapping(value = "", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> createRequest(
		RequestDTO requestDTO, BindingResult bindingResult, HttpServletRequest request
	) {

		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();

			logger.debug("Binding errors {}", requestDTO.getAuthor());

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		Request saved = requestService.createRequest(requestDTO);

		return ResponseEntity
			.created(URI.create("/api/requests/" + saved.getIdRequest()))
			.body(requestDTOassembler.toModel(saved));
	}

	@Secured("ROLE_USER")
	@PutMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> updateRequest(
		@PathVariable("id") long id, UserRegisterDTO user, @RequestBody RequestDTO requestDTO
	) { // TODO DA JE ONAJ KOJI UPDATEA REQUEST I AUTOR TOG REQUESTA...
		if (!requestDTO.getIdRequest().equals(id))
			throw new IllegalArgumentException("Request ID must be preserved"); //FIXME handler

		if (user.equals(requestDTO.getAuthor())) {
			RequestDTO updated
				= requestDTOassembler.toModel(requestService.updateRequest(requestDTO));
			return new ResponseEntity<RepresentationModel<RequestDTO>>(updated, HttpStatus.OK);
		} else
			return (ResponseEntity<?>) ResponseEntity.badRequest();

	}

	@Secured("ROLE_USER")
	@PutMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> blockingRequest(
		@PathVariable("id") long id, UserRegisterDTO user, @RequestBody RequestDTO requestDTO
	) {
		if (!requestDTO.getIdRequest().equals(id))
			throw new IllegalArgumentException("Request ID must be preserved"); //FIXME handler

		if (user.equals(requestDTO.getAuthor())) // is author
		{
			RequestDTO blocked
				= requestDTOassembler.toModel(requestService.blockRequest(requestDTO));
			return new ResponseEntity<RepresentationModel<RequestDTO>>(blocked, HttpStatus.OK);
		} else
			return (ResponseEntity<?>) ResponseEntity.badRequest();

	}

	@Secured("ROLE_USER")
	@PutMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> executeRequest(
		@PathVariable("id") long id, UserRegisterDTO user, @RequestBody RequestDTO requestDTO
	) {
		if (!requestDTO.getIdRequest().equals(id))
			throw new IllegalArgumentException("Request ID must be preserved"); //FIXME handler

		if (user.equals(requestDTO.getAuthor())) // is author
		{
			RequestDTO executed
				= requestDTOassembler.toModel(requestService.executeRequest(requestDTO));
			return new ResponseEntity<RepresentationModel<RequestDTO>>(executed, HttpStatus.OK);
		} else
			return (ResponseEntity<?>) ResponseEntity.badRequest();

	}

	@Secured("ROLE_USER")
	@DeleteMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" }) // TODO DA JE ONAJ KOJI BRISE REQUEST I AUTOR TOG REQUESTA...
	public ResponseEntity<?> deleteRequest(
		@PathVariable("id") long requestId, UserRegisterDTO user,@RequestBody RequestDTO requestDTO,
		BindingResult bindingResult, HttpServletRequest request
	) {
		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();
			logger.debug("Binding errors {}", errors);
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		if (user.equals(requestDTO.getAuthor())) {
			Request deleted = requestService.deleteRequest(requestId);
			return ResponseEntity.ok(deleted);
		} else
			return (ResponseEntity<?>) ResponseEntity.badRequest();
	}
}
