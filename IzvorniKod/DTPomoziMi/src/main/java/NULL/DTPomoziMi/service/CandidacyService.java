package NULL.DTPomoziMi.service;

import java.util.Optional;

import org.modelmapper.MappingException;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.model.Candidacy;
import NULL.DTPomoziMi.web.DTO.CandidacyDTO;

public interface CandidacyService {

	/**
	 * Saves a given entity. Use the returned instance for further operations as the
	 * save operation might have changed the entity instance completely.
	 *
	 * @param entity must not be {@literal null}.
	 * @return the saved entity; will never be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@literal entity} is
	 *                                  {@literal null}.
	 */
	Candidacy save(CandidacyDTO entity);

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
	 * 
	 * @throws MappingException         - if a runtime error occurs while mapping
	 */
	Iterable<Candidacy> saveAll(Iterable<CandidacyDTO> entities);

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none
	 *         found.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
	Optional<Candidacy> findById(Long id);

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id
	 * @throws EntityMissingException   - if element with given <code>id</code> does
	 *                                  not exist
	 * @throws IllegalArgumentException - if id is null.
	 */
	Candidacy fetch(Long id);

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
	Iterable<Candidacy> findAll();

	/**
	 * Returns all instances of the type {@code T} with the given IDs.
	 * <p>
	 * If some or all ids are not found, no entities are returned for these IDs.
	 * <p>
	 * Note that the order of elements in the result is not guaranteed.
	 *
	 * @param ids must not be {@literal null} nor contain any {@literal null}
	 *            values.
	 * @return guaranteed to be not {@literal null}. The size can be equal or less
	 *         than the number of given {@literal ids}.
	 * @throws IllegalArgumentException in case the given {@link Iterable ids} or
	 *                                  one of its items is {@literal null}.
	 */
	Iterable<Candidacy> findAllById(Iterable<Long> ids);

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
	 */
	Candidacy deleteById(Long id);

	/**
	 * Deletes the given entities.
	 *
	 * @param entities must not be {@literal null}. Must not contain {@literal null}
	 *                 elements.
	 * @throws IllegalArgumentException in case the given {@literal entities} or one
	 *                                  of its entities is {@literal null}.
	 */
	Iterable<Candidacy> deleteAll(Iterable<CandidacyDTO> entities);

	/**
	 * Deletes all entities managed by the repository.
	 */
	Iterable<Candidacy> deleteAll();

}
