package NULL.DTPomoziMi.web.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.service.RatingService;
import NULL.DTPomoziMi.web.DTO.RatingDTO;
import NULL.DTPomoziMi.web.assemblers.RatingDTOAssembler;

/**
 * The Class RatingController.
 */
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	
	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/** The rating DTO assembler. */
	@Autowired
	private RatingDTOAssembler ratingDTOAssembler;

	/** The rating service. */
	@Autowired
	private RatingService ratingService;

	/**
	 * Gets the ratings.
	 *
	 * @param pageable  the pageable
	 * @param assembler the assembler
	 * @return the ratings
	 */
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
			throw e;
		}
	}

	/**
	 * Gets the rating by id.
	 *
	 * @param id the id
	 * @return the rating by id
	 */
	@GetMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> getRatingById(@PathVariable("id") Long id) {
		try {
			return ResponseEntity.ok(ratingDTOAssembler.toModel(ratingService.fetch(id)));
		} catch (Exception e) {
			logger.debug("Exception {} while fetching rating by id", e.getMessage());
			throw e;
		}
	}

	/**
	 * Creates the rating.
	 *
	 * @param rating        the rating
	 * @param bindingResult the binding result
	 * @return the response entity
	 */
	@PostMapping(value = "", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> createRating(
		@ModelAttribute RatingDTO rating, BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();

			String errors = stringifyErrors(fieldErrors);

			logger.debug("Binding errors {}", errors);

			throw new IllegalArgumentException(errors);
		}

		try {
			Rating saved = ratingService.create(rating);
			return ResponseEntity.created(URI.create("/api/ratings" + saved.getIdRating()))
				.body(ratingDTOAssembler.toModel(saved));
		} catch (Exception e) {
			logger.debug("Exception {} while creating rating", e.getMessage());
			throw e;
		}
	}

	/**
	 * Update rating.
	 *
	 * @param idRating the id rating
	 * @param ratingDTO the rating DTO
	 * @return the response entity
	 */

	@PutMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> updateRating(
		@PathVariable("id") long idRating, @RequestBody RatingDTO ratingDTO // TODO binding result... model attr? 
	) {
		try {
			return ResponseEntity
				.ok(ratingDTOAssembler.toModel(ratingService.update(ratingDTO, idRating)));
		} catch (Exception e) {
			logger.debug("Exception {} while updating rating", e.getMessage());
			throw e;
		}

	}
	
	/**
	 * Delete rating.
	 *
	 * @param idRating the id rating
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{id}", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> deleteRating(@PathVariable("id") long idRating){
		return ResponseEntity.ok(ratingDTOAssembler.toModel(ratingService.deleteById(idRating)));
	}

	/**
	 * The Class Counter.
	 */
	class Counter {
		
		/** The c. */
		public int c;

		/**
		 * Instantiates a new counter.
		 *
		 * @param c the c
		 */
		public Counter(int c) { this.c = c; }
	}

	/**
	 * Stringify errors.
	 *
	 * @param list the list
	 * @return the string
	 */
	private String stringifyErrors(List<FieldError> list) {
		Counter c = new Counter(0);
		return list.stream().reduce(
			"[",
			(partialRes, e) -> {
				c.c++;
				return partialRes + (c.c == 1 ? "" : ", ") + e.toString();
			}, (s1, s2) -> s1 + s2
		) + "]";
	}
}
