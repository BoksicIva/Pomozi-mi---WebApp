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

import NULL.DTPomoziMi.exception.IllegalAccessException;
import NULL.DTPomoziMi.model.RequestStatus;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.util.UserPrincipalGetter;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class DeleteRequestTest {

    @Autowired
    private RequestServiceImpl service;

    private UserPrincipal principal;

    //brisanje vlastitog zahtjeva
    @Test
    @WithUserDetails(value="matea.lipovac@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test1(){

        principal = UserPrincipalGetter.getPrincipal();
        assertEquals(RequestStatus.DELETED, service.deleteRequest(31l, principal).getStatus());
    }

    //admin brise zahtjev
    @Test
    @WithUserDetails(value="jan.rocek@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test2()
    {
        principal = UserPrincipalGetter.getPrincipal();
        assertEquals(RequestStatus.DELETED, service.deleteRequest(2, principal).getStatus());
    }

    //nevlasteno brisanje tudeg zahtjeva
    @Test
    @WithUserDetails(value="matea.lipovac@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test3(){
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(IllegalAccessException.class, ()->service.deleteRequest(39, principal).getStatus());
    }

    //zahtjev već ima izvrsitelja
    @Test
    @WithUserDetails(value="matea.lipovac@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test4()
    {
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(IllegalAccessException.class, ()->service.deleteRequest(37, principal).getStatus());
    }
    //neprijavljen korisnik
    @Test
    @Transactional
    void test5(){
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(AuthenticationCredentialsNotFoundException.class, ()->service.deleteRequest(6, principal).getStatus());
    }
}
