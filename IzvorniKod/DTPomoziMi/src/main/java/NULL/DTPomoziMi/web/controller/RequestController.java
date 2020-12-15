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
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

	@GetMapping(value = "", produces = { "application/json; charset=UTF-8" })
	public
			ResponseEntity<?>
			getAll(@PageableDefault Pageable pageable, PagedResourcesAssembler<Request> assembler) {

		Page<Request> page = requestService.findAll(pageable);

		PagedModel<RequestDTO> pagedModel
				= assembler
						.toModel(
								page, requestDTOassembler,
								linkTo(methodOn(RequestController.class).getAll(null, null))
										.withSelfRel()
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
	public
			ResponseEntity<?>
			updateRequest(@PathVariable("id") long id, @RequestBody RequestDTO requestDTO) { // TODO DA JE ONAJ KOJI UPDATEA REQUEST I AUTOR TOG REQUESTA...
		if (!requestDTO.getIdRequest().equals(id))
			throw new IllegalArgumentException("Request ID must be preserved"); //FIXME handler

		RequestDTO updated = requestDTOassembler.toModel(requestService.updateRequest(requestDTO));

		return new ResponseEntity<RepresentationModel<RequestDTO>>(updated, HttpStatus.OK);
	}

	@Secured("ROLE_USER")
	@DeleteMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> deleteRequest(@PathVariable("id") long requestId) {// TODO DA JE ONAJ KOJI BRISE REQUEST I AUTOR TOG REQUESTA...

		RequestDTO deleted = requestDTOassembler.toModel(requestService.deleteRequest(requestId));
		
		return ResponseEntity.ok(deleted);

	}
}
