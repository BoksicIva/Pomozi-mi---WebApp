package NULL.DTPomoziMi.service.impl;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.repository.UserRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.UserPrincipalGetter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")

public class FetchTest {
    @Autowired
    private UserService service;

    private UserPrincipal principal;

    @Autowired
    private UserRepo userRepo;

    //dohvat samog sebe
    @Test
    @WithUserDetails(value = "jan.rocek@gmail.com", userDetailsServiceBeanName = "myUserDetailsService")
    @Transactional
    void test1() {
        principal = UserPrincipalGetter.getPrincipal();
        assertEquals(principal.getUser(), service.fetch(3));
    }

    //dohvat postojeceg korisnika
    @Test
    @WithUserDetails(value = "jan.rocek@gmail.com", userDetailsServiceBeanName = "myUserDetailsService")
    @Transactional
    void test2() {
        principal = UserPrincipalGetter.getPrincipal();
        assertEquals(userRepo.findByEmail("matea.lipovac@gmail.com"), service.fetch(12));
    }

    //dohvat nepostojeceg korisnika
    @Test
    @WithUserDetails(value = "jan.rocek@gmail.com", userDetailsServiceBeanName = "myUserDetailsService")
    @Transactional
    void test3() {
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(EntityMissingException.class, ()-> service.fetch(90));
    }

    //neprijavljeni korisnik
    @Test
    @Transactional
    void test4() {
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> service.fetch(12));

    }
}
