package NULL.DTPomoziMi.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import NULL.DTPomoziMi.model.User;

public interface UserRepo extends CrudRepository<User, Long>{

	User findByEmail(String email);
	
	@Modifying
	@Query("update Korisnik u set u.token = :token where u.email = :email")
	void updateToken(@Param(value = "token") String token, @Param(value = "email") String email);
	
	@Query("SELECT TOKEN FROM KORISNIK WHERE EMAIL= :email AND AKTIVAN = TRUE")
	String getTokenByEmail(@Param(value = "email") String email);
	
}
