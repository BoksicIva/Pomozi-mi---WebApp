/*
 * 
 */
package NULL.DTPomoziMi.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	 * Block request.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@PutMapping(value = "/block/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> blockRequest(@PathVariable("id") long id) {

		RequestDTO blocked = requestDTOassembler.toModel(requestService.blockRequest(id));
		return new ResponseEntity<>(blocked, HttpStatus.OK);
	}

	/**
	 * Creates the request.
	 *
	 * @param requestDTO    the request DTO
	 * @param bindingResult the binding result
	 * @param request       the request
	 * @return the response entity
	 */
	@PostMapping(value = "", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> createRequest(
		RequestDTO requestDTO, BindingResult bindingResult, HttpServletRequest request
	) {
		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();

			logger.debug("Binding errors {}", errors);

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		Request saved = requestService.createRequest(requestDTO);

		return ResponseEntity.created(URI.create("/api/requests/" + saved.getIdRequest()))
			.body(requestDTOassembler.toModel(saved));
	}

	/**
	 * Delete request.
	 *
	 * @param requestId     the request id
	 * @param user          the user
	 * @param requestDTO    the request DTO
	 * @param bindingResult the binding result
	 * @param request       the request
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> deleteRequest(@PathVariable("id") long requestId) {
		Request deleted = requestService.deleteRequest(requestId);
		return ResponseEntity.ok(requestDTOassembler.toModel(deleted));
	}

	/**
	 * Returns active requests, based on the radius and logged in User's location.
	 * The radius is optional and if not set, 0 is treated as default value. If the
	 * logged in user hasn't set his/her location then only requests without
	 * location are returned.
	 */
	@GetMapping(value = "/active", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> getActive(
		@PageableDefault Pageable pageable, PagedResourcesAssembler<Request> assembler,
		@RequestParam(name = "radius", required = false) Double radius
	) {

		Page<Request> page = requestService.getAllActiveRequests(pageable, radius);

		PagedModel<RequestDTO> pagedModel = assembler.toModel(
			page, requestDTOassembler,
			linkTo(methodOn(RequestController.class).getActive(pageable, null, radius))
				.withSelfRel()
		);

		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}

	/**
	 * Returns all authored requests by author's id as a map of finalized, blocked
	 * and active requests
	 */
	@GetMapping(value = "/authored/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> getAuthoredRequests(@PathVariable(name = "id") Long userId) {
		EntityModel<?> model = EntityModel.of(requestService.getAuthoredRequests(userId));
		model.add(linkTo(methodOn(getClass()).getAuthoredRequests(userId)).withSelfRel());

		return ResponseEntity.ok(model);
	}

	/**
	 * Gets the request by id
	 *
	 * @param id the id
	 * @return the request
	 */
	@GetMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<RequestDTO> getRequest(@PathVariable("id") long id) {

		RequestDTO req = requestDTOassembler.toModel(requestService.getRequestbyId(id));

		return new ResponseEntity<>(req, HttpStatus.OK);
	}

	/**
	 * Mark executed.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@PutMapping(value = "/markExecuted/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> markExecuted(@PathVariable("id") long id) {
		RequestDTO req = requestDTOassembler.toModel(requestService.markExecuted(id));
		return ResponseEntity.ok(req);
	}

	/**
	 * Pick for execution.
	 *
	 * @param id         the id
	 * @param user       the user
	 * @param requestDTO the request DTO
	 * @return the response entity
	 */
	@PutMapping(value = "pickForExecution/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> pickForExecution(@PathVariable("id") long id) {

		RequestDTO executed = requestDTOassembler.toModel(requestService.pickForExecution(id));
		return new ResponseEntity<>(executed, HttpStatus.OK);
	}

	/**
	 * Update request.
	 *
	 * @param id         the id
	 * @param requestDTO the request DTO
	 * @return the response entity
	 */
	@PutMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> updateRequest(
		@PathVariable("id") long id, @RequestBody RequestDTO requestDTO
	) {
		if (!requestDTO.getIdRequest().equals(id))
			throw new IllegalArgumentException("Request ID must be preserved");

		RequestDTO updated = requestDTOassembler.toModel(requestService.updateRequest(requestDTO));
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
}
