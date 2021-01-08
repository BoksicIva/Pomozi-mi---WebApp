package NULL.DTPomoziMi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import NULL.DTPomoziMi.model.Request;

public interface RequestRepo extends PagingAndSortingRepository<Request, Long>, JpaSpecificationExecutor<Request> {

	@Query(nativeQuery = true, value = "SELECT CASE WHEN (count(*) > 0) THEN true ELSE false END "
			+ "FROM (SELECT z.id_zahtjev, count(*) as cnt FROM zahtjev z LEFT OUTER JOIN ocjenjivanje r ON z.id_zahtjev=r.id_zahtjev WHERE z.status='FINALIZED' "
			+ "AND (z.id_autor = :p OR z.id_izvrsitelj = :p ) GROUP BY z.id_zahtjev HAVING count(*) < 2) counted")
	boolean existsUnratedFinalizedRequest(@Param("p") long idParticipant);
	
	@EntityGraph(attributePaths = { "ratings" }, type = EntityGraphType.FETCH)
	List<Request> findAll(Specification<Request> specification);

	@EntityGraph(attributePaths = { "ratings" }, type = EntityGraphType.FETCH)
	Optional<Request> findById(long id); 
	
}