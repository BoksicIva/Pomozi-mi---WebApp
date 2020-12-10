package NULL.DTPomoziMi.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import NULL.DTPomoziMi.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = false, value = "update Korisnik u set u.token = :token where u.email = :email")
	void updateToken(@Param(value = "token") String token, @Param(value = "email") String email);
	
	@Query(nativeQuery = false, value = "SELECT token FROM Korisnik WHERE EMAIL = :email AND enabled = TRUE")
	String getTokenByEmail(@Param(value = "email") String email);

	//https://stackoverflow.com/questions/39036771/how-to-map-pageobjectentity-to-pageobjectdto-in-spring-data-rest
	//https://bezkoder.com/spring-boot-pagination-filter-jpa-pageable/
	//So when we want to get pagination (with or without filter) in the results, we just add Pageable to the definition of the method as a parameter.
	Page<User> findByFirstNameIsLike(String firstName, Pageable pageable);
	Page<User> findByLastNameIsLike(String firstName, Pageable pageable);
	Page<User> findByEnabled(boolean enabled, Pageable pageable);

}
