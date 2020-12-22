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
	 * Returns a {@link Page} of entities meeting the paging restriction provided in
	 * the {@code Pageable} object.
	 *
	 * @param pageable
	 * @return a page of entities
	 */
	Page<Rating> findAll(Pageable pageable);

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
	 * Saves all given entities.
	 *
	 * @param entities must not be {@literal null} nor must it contain
	 *                 {@literal null}.
	 * @return the saved entities; will never be {@literal null}. The returned
	 *         {@literal Iterable} will have the same size as the
	 *         {@literal Iterable} passed as an argument.
	 * @throws IllegalArgumentException in case the given {@link Iterable entities}
	 *                                  or one of its entities is {@literal null}.
	 * @throws IllegalArgumentException - if source or destinationType are null
	 * @throws ConfigurationException   - if the ModelMapper cannot find or create a
	 *                                  TypeMap for the arguments
	 * @throws MappingException         - if a runtime error occurs while mapping
	 */
	Iterable<Rating> saveAll(Iterable<RatingDTO> entities);

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
	 * Returns whether an entity with the given id exists.
	 *
	 * @param id must not be {@literal null}.
	 * @return {@literal true} if an entity with the given id exists,
	 *         {@literal false} otherwise.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
	boolean existsById(Long id);

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 */
	Iterable<Rating> findAll();

	/**
	 * Returns the number of entities available.
	 *
	 * @return the number of entities.
	 */
	long count();

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
	 * Deletes the given entities.
	 *
	 * @param entities must not be {@literal null}. Must not contain {@literal null}
	 *                 elements.
	 * @throws IllegalArgumentException in case the given {@literal entities} or one
	 *                                  of its entities is {@literal null}.
	 * @return deleted entities
	 */
	Iterable<Rating> deleteAll(Iterable<RatingDTO> entities);

	/**
	 * Deletes all entities managed by the repository.
	 * 
	 * @return deleted entities
	 */
	Iterable<Rating> deleteAll();

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
}
