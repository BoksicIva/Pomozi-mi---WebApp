package NULL.DTPomoziMi.service;

import java.util.Optional;

import org.modelmapper.ConfigurationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.MappingException;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.web.DTO.RatingDTO;

public interface RatingService {

	/**
	 * Saves a given entity. Use the returned instance for further operations as the
	 * save operation might have changed the entity instance completely.
	 *
	 * @param entity must not be {@literal null}.
	 * @return the saved entity; will never be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@literal entity} is
	 *                                  {@literal null}.
	 * @throws IllegalArgumentException - if source or destinationType are null
	 * @throws ConfigurationException   - if the ModelMapper cannot find or create a
	 *                                  TypeMap for the arguments
	 * @throws MappingException         - if a runtime error occurs while mapping
	 */
	Rating save(RatingDTO entity);

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none
	 *         found.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
	Optional<Rating> findById(Long id);

	/**
	 * Deletes the entity with the given id.
	 *
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@literal id} is
	 *                                  {@literal null}
	 * @throws EntityMissingException   - if element with given <code>id</code> does
	 *                                  not exist
	 * @return deleted entity
	 */
	Rating deleteById(Long id);

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id
	 * @throws EntityMissingException   - if element with given <code>id</code> does
	 *                                  not exist
	 * @throws IllegalArgumentException - if id is null.
	 */
	Rating fetch(Long id);

	/**
	 * Creates the rating.
	 *
	 * @param rating    the rating
	 * @param idUser    the id user
	 * @param idRequest the id request
	 * @return the rating
	 * @throws MappingException - if a runtime error occurs while mapping
	 */
	Rating create(RatingDTO rating, long idUser, Long idRequest);

	/**
	 * Update rating.
	 *
	 * @param rating   the rating
	 * @param ratingId the rating id
	 * @return the rating
	 * 
	 * @throws MappingException       - if a runtime error occurs while mapping
	 * @throws EntityMissingException - if element with given <code>id</code> does
	 *                                not exist
	 */
	Rating update(RatingDTO rating, long ratingId);

	/**
	 * Gets the rating by id.
	 *
	 * @param id the id
	 * @return the rating by id
	 * @throws EntityMissingException - if element with given <code>id</code> does
	 *                                not exist
	 */
	Rating getRatingById(Long id);

	/**
	 * Find by rated.
	 *
	 * @param pageable the pageable
	 * @param rated    the rated
	 * @return the page
	 * @throws EntityMissingException - if element with given <code>id</code> does
	 *                                not exist
	 */
	Page<Rating> findByRated(Pageable pageable, long userID);
}
