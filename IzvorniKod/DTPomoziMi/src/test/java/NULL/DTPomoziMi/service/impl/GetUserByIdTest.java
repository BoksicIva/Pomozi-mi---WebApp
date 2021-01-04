package NULL.DTPomoziMi.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.UserPrincipalGetter;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")

class GetUserByIdTest {

	@Autowired
	private UserService service;

	private UserPrincipal principal;

	//dobar id
	@Test
	@WithUserDetails(
		value = "jan.rocek@gmail.com", userDetailsServiceBeanName = "myUserDetailsService"
	)
	@Transactional
	void test1() {
		principal = UserPrincipalGetter.getPrincipal();
		assertEquals("jan.rocek@gmail.com", service.getUserByID(3l, principal).getEmail());
	}

	//nepostojeci id
	@Test
	@WithUserDetails(
		value = "jan.rocek@gmail.com", userDetailsServiceBeanName = "myUserDetailsService"
	)
	@Transactional
	void test2() {
		principal = UserPrincipalGetter.getPrincipal();
		assertThrows(EntityMissingException.class, () -> service.getUserByID(110l, principal));
	}

	//neprijavljen korisnik
	@Test
	void test3() {
		assertThrows(
			AuthenticationCredentialsNotFoundException.class,
			() -> service.getUserByID(110l, principal)
		);
	}

}
