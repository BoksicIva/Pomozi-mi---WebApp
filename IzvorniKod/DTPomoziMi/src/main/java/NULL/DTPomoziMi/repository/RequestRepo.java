package NULL.DTPomoziMi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.model.RequestStatus;
import NULL.DTPomoziMi.model.User;

public interface RequestRepo extends CrudRepository<Request, Long> {

	Page<Request> findAll(Pageable pageable);

	List<Request> findByStatusOrderByIdRequest(RequestStatus status);

	List<Request> findByStatusAndAuthor(RequestStatus status, User author);

}