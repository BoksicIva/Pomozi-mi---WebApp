package NULL.DTPomoziMi.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import NULL.DTPomoziMi.model.Request;

public interface RequestRepo extends PagingAndSortingRepository<Request, Long>, JpaSpecificationExecutor<Request>{
	

}