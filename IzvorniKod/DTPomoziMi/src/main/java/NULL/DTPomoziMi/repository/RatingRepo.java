package NULL.DTPomoziMi.repository;
import NULL.DTPomoziMi.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepo extends CrudRepository<Rating, Long> {

    Page<Rating> findAll(Pageable pageable);

}
