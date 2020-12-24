package NULL.DTPomoziMi.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.MappingException;
import org.springframework.hateoas.CollectionModel;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.exception.IllegalActionException;
import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.web.DTO.CreateRequestDTO;
import NULL.DTPomoziMi.web.DTO.RequestDTO;

public interface RequestService {

	/**
	 * Block request.
	 *
	 * @param idRequest the id request
	 * @return the request
	 * @throws EntityMissingException - if element with given <code>id</code> does
	 *                                not exist
	 * @throws IllegalAccessException - if user who is trying to block the request
	 *                                is not author or admin
	 */
	Request blockRequest(long idRequest);

	/**
	 * Creates the request.
	 *
	 * @param request the request
	 * @return the created request
	 * 
	 * @throws MappingException     - if a runtime error occurs while mapping DTO to
	 *                              entity
	 * @throws NullPointerException if given {@literal request} is {@literal null}
	 *                              reference
	 */
	Request createRequest(CreateRequestDTO request);

	/**
	 * Delete request.
	 *
	 * @param id_zahtjev the id zahtjev
	 * @return the request
	 * @throws IllegalAccessException if user is not author or admin
	 * @throws EntityMissingException - if element with given <code>id</code> does
	 *                                not exist
	 */
	Request deleteRequest(long id_zahtjev);

	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id
	 * @throws EntityMissingException - if element with given <code>id</code> does
	 *                                not exist
	 */
	Request fetch(long requestId);

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	/* Page<Request> findAll(Pageable pageable); */

	/**
	 * Gets the all active requests.
	 *
	 * @param pageable the pageable
	 * @param radius   the radius
	 * @return the all active requests
	 */
	Page<Request> getAllActiveRequests(Pageable pageable, Double radius);

	/**
	 * @param userID
	 * @return
	 * @throws IllegalAccessException if given {@literal userID} is not the same as
	 *                                id of authenticated user
	 */
	Map<String, CollectionModel<RequestDTO>> getAuthoredRequests(long userID);

	/**
	 * Gets request by id and checks if user is permitted to see such resource. If
	 * not, then throws {@link IllegalAccessException}.
	 * 
	 * @throws EntityMissingException - if element with given <code>id</code> does
	 *                                not exist
	 * @throws IllegalAccessException - if user is not allowed to access that
	 *                                request
	 */
	Request getRequestbyId(long id_zahtjev);

	/**
	 * Mark executed.
	 *
	 * @param idRequest the id request
	 * @return the request
	 * @throws EntityMissingException - if element with given <code>id</code> does
	 *                                not exist
	 * @throws IllegalAccessException - if authenticated user is not author
	 * @throws IllegalActionException - if request cannot be marked as finalized
	 */
	Request markExecuted(long idRequest);

	/**
	 * Execute request.
	 *
	 * @param request the request
	 * @return the request
	 * @throws EntityMissingException - if element with given <code>id</code> does
	 *                                not exist
	 */
	Request pickForExecution(long idRequest);

	/**
	 * Update request.
	 *
	 * @param idRequest  the id request
	 * @param requestDTO the request DTO
	 * @return the request
	 * @throws NullPointerException   if given {@literal request} is {@literal null}
	 *                                reference
	 * @throws EntityMissingException - if element with given <code>id</code> does
	 *                                not exist
	 */
	Request updateRequest(long idRequest, RequestDTO requestDTO);

	/**
	 * Back off from execution.
	 *
	 * @param id the id
	 * @return the request
	 * 
	 * @throws EntityMissingException - if element with given <code>id</code> does
	 *                                not exist
	 */
	Request backOff(long id);
}