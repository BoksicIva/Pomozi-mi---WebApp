package NULL.DTPomoziMi.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.service.RatingService;
import NULL.DTPomoziMi.web.DTO.RatingDTO;
import NULL.DTPomoziMi.web.assemblers.RatingDTOAssembler;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/ratings")
public class RatingController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RatingDTOAssembler ratingDTOAssembler;

	@Autowired
	private RatingService ratingService;

	@GetMapping(value = "", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> getRatings(
		@PageableDefault Pageable pageable, PagedResourcesAssembler<Rating> assembler
	) {
		try {
			Page<Rating> ratingsPage = ratingService.findAll(pageable);

			PagedModel<RatingDTO> pm = assembler.toModel(ratingsPage, ratingDTOAssembler);

			return ResponseEntity.ok(pm);
		} catch (Exception e) {
			logger.debug("Exception {} while fetching ratings page", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
