package NULL.DTPomoziMi.service;

import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.web.DTO.RequestDTO;

import java.util.List;

public interface RequestService {

    void createRequest(RequestDTO request);
    /**
     *
     * @throws java.util.NoSuchElementException
     */
    void updateRequest(RequestDTO request);
    void deleteRequest(Long id_zahtjev);
    /**
     *
     * @throws java.util.NoSuchElementException
     */
    Request getRequestbyId(Long id_zahtjev);

    List<Request> findAll();
}
