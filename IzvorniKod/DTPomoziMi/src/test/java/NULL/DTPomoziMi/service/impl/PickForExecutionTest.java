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

import NULL.DTPomoziMi.exception.IllegalActionException;
import NULL.DTPomoziMi.model.RequestStatus;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.util.UserPrincipalGetter;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")

public class PickForExecutionTest {
    @Autowired
    private RequestServiceImpl service;

    private UserPrincipal principal;

    //uspjesno odabrano izvrsavanje
    @Test
    @WithUserDetails(value="iva.boksic@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test1(){
        principal = UserPrincipalGetter.getPrincipal();
        assertEquals(RequestStatus.EXECUTING, service.pickForExecution(14, principal).getStatus());
    }
    //neaktivan zahtjev
    @Test
    @WithUserDetails(value="iva.boksic@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test2(){
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(IllegalActionException.class, ()->service.pickForExecution(2, principal));
    }
    //neprijavljen korisnik
    @Test
    @Transactional
    void test3(){
        assertThrows(AuthenticationCredentialsNotFoundException.class, ()->service.deleteRequest(14, principal).getStatus());
    }
}
