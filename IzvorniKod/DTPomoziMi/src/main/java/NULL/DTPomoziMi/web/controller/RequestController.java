/*
 * 
 */
package NULL.DTPomoziMi.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import NULL.DTPomoziMi.exception.BindingException;
import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.RequestService;
import NULL.DTPomoziMi.util.Common;
import NULL.DTPomoziMi.web.DTO.CreateRequestDTO;
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
	@PatchMapping(value = "/block/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> blockRequest(
		@PathVariable("id") long id, @AuthenticationPrincipal UserPrincipal principal
	) {
		try {
			RequestDTO blocked
				= requestDTOassembler.toModel(requestService.blockRequest(id, principal));
			return new ResponseEntity<>(blocked, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
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
		@RequestBody @Valid CreateRequestDTO requestDTO, BindingResult bindingResult,
		HttpServletRequest request, @AuthenticationPrincipal UserPrincipal principal
	) {
		if (bindingResult.hasErrors()) hasErrors(bindingResult);

		try {
			Request saved = requestService.createRequest(requestDTO, principal);

			return ResponseEntity
				.created(URI.create("/api/requests/" + saved.getIdRequest()))
				.body(requestDTOassembler.toModel(saved));
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
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
	public ResponseEntity<?> deleteRequest(
		@PathVariable("id") long requestId, @AuthenticationPrincipal UserPrincipal principal
	) {
		try {
			Request deleted = requestService.deleteRequest(requestId, principal);
			return ResponseEntity.ok(requestDTOassembler.toModel(deleted));
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	/**
	 * Returns active requests, based on the radius and logged in User's location.
	 * The radius is optional and if not set, 0 is treated as default value. If the
	 * logged in user hasn't set his/her location then only requests without
	 * location are returned.
	 *
	 * @param pageable  the pageable
	 * @param assembler the assembler
	 * @param radius    the radius
	 * @return the active
	 */
	@GetMapping(value = "/active", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> getActive(
		@PageableDefault Pageable pageable, PagedResourcesAssembler<Request> assembler,
		@RequestParam(name = "radius", required = false) Double radius,
		@AuthenticationPrincipal UserPrincipal principal
	) {
		try {
			Page<Request> page = requestService.getAllActiveRequests(pageable, radius, principal);

			PagedModel<RequestDTO> pagedModel = assembler
				.toModel(
					page, requestDTOassembler,
					linkTo(
						methodOn(RequestController.class)
							.getActive(pageable, null, radius, principal)
					).withSelfRel()
				);

			return new ResponseEntity<>(pagedModel, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	/**
	 * Returns all authored requests by author's id as a map of finalized, blocked
	 * and active requests.
	 *
	 * @param userId the user id
	 * @return the authored requests
	 */
	@GetMapping(value = "/authored/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> getAuthoredRequests(
		@PathVariable(name = "id") Long userId, @AuthenticationPrincipal UserPrincipal principal
	) {
		try {
			EntityModel<?> model
				= EntityModel.of(requestService.getAuthoredRequests(userId, principal));
			model
				.add(
					linkTo(methodOn(getClass()).getAuthoredRequests(userId, principal))
						.withSelfRel()
				);

			return ResponseEntity.ok(model);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	/**
	 * Gets the request by id.
	 *
	 * @param id the id
	 * @return the request
	 */
	@GetMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<RequestDTO> getRequest(
		@PathVariable("id") long id, @AuthenticationPrincipal UserPrincipal principal
	) {
		try {
			RequestDTO req
				= requestDTOassembler.toModel(requestService.getRequestbyId(id, principal));

			return new ResponseEntity<>(req, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	/**
	 * Mark executed.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@PatchMapping(value = "/markExecuted/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> markExecuted(
		@PathVariable("id") long id, @AuthenticationPrincipal UserPrincipal principal
	) {
		try {
			RequestDTO req
				= requestDTOassembler.toModel(requestService.markExecuted(id, principal));
			return ResponseEntity.ok(req);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	/**
	 * Pick for execution.
	 *
	 * @param id         the id
	 * @param user       the user
	 * @param requestDTO the request DTO
	 * @return the response entity
	 */
	@PatchMapping(value = "pickForExecution/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> pickForExecution(
		@PathVariable("id") long id, @AuthenticationPrincipal UserPrincipal principal
	) {
		try {
			RequestDTO executed
				= requestDTOassembler.toModel(requestService.pickForExecution(id, principal));
			return new ResponseEntity<>(executed, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	@PatchMapping(value = "backOff/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> backOff(
		@PathVariable("id") long id, @AuthenticationPrincipal UserPrincipal principal
	) {
		try {
			return ResponseEntity
				.ok(requestDTOassembler.toModel(requestService.backOff(id, principal)));
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
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
		@PathVariable("id") long id, @RequestBody @Valid RequestDTO requestDTO,
		BindingResult bindingResult, @AuthenticationPrincipal UserPrincipal principal
	) {
		if (bindingResult.hasErrors()) hasErrors(bindingResult);

		try {
			RequestDTO updated = requestDTOassembler
				.toModel(requestService.updateRequest(id, requestDTO, principal));
			return new ResponseEntity<>(updated, HttpStatus.OK);
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
}
