package NULL.DTPomoziMi.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import NULL.DTPomoziMi.model.User;

public interface UserRepo extends CrudRepository<User, Long>{

	User findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = false, value = "update Korisnik u set u.token = :token where u.email = :email")
	void updateToken(@Param(value = "token") String token, @Param(value = "email") String email);
	
	@Query(nativeQuery = false, value = "SELECT token FROM Korisnik WHERE EMAIL = :email AND enabled = TRUE")
	String getTokenByEmail(@Param(value = "email") String email);
	
}
