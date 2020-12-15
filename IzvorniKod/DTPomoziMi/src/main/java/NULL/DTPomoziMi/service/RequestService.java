package NULL.DTPomoziMi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.web.DTO.RequestDTO;

public interface RequestService {

	Request createRequest(RequestDTO request);

	/**
	 *
	 * @throws java.util.EntityNotFoundException
	 */
	Request updateRequest(RequestDTO request);

	Request deleteRequest(long id_zahtjev);

	/**
	 *
	 * @throws java.util.NoSuchElementException
	 */
	Request getRequestbyId(long id_zahtjev);

	Page<Request> findAll(Pageable pageable);
	
	Request fetch(long requestId);
}
