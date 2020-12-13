package NULL.DTPomoziMi.repository;

import NULL.DTPomoziMi.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepo extends CrudRepository<Request, Long> {

    Page<Request> findAll(Pageable pageable);

}
