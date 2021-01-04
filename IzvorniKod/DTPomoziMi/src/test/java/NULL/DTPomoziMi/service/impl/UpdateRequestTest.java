package NULL.DTPomoziMi.service.impl;

import NULL.DTPomoziMi.repository.RequestRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.util.UserPrincipalGetter;
import NULL.DTPomoziMi.web.DTO.RequestDTO;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")

public class UpdateRequestTest {
    @Autowired
    private RequestServiceImpl service;

    @Autowired
    private RequestRepo requestRepo;

    private RequestDTO requestDTO;
    private UserPrincipal principal;
    @BeforeEach
    void setup(){
        requestDTO = new RequestDTO();
        requestDTO.setIdRequest(19l);
        requestDTO.setDescription("masna kobasa");
        requestDTO.setPhone("0981904779");
    }

    //uspjsan update
    @Test
    @WithUserDetails(value="dominik.curic@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test1(){
        principal = UserPrincipalGetter.getPrincipal();
        assertAll(
                () -> assertEquals(requestDTO.getIdRequest(), service.updateRequest(19l, requestDTO, principal).getIdRequest()),
                () -> assertEquals(requestDTO.getDescription(), service.updateRequest(19l, requestDTO, principal).getDescription()),
                () -> assertEquals(requestDTO.getPhone(), service.updateRequest(19l, requestDTO, principal).getPhone())
        );
    }

    //id requesta i id reguestDTOa razliciti
    @Test
    @WithUserDetails(value="dominik.curic@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test2(){
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(IllegalArgumentException.class, ()->service.updateRequest(3, requestDTO, principal));
    }

    //nije autor
    @Test
    @WithUserDetails(value="iva.boksic@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test3(){
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(IllegalAccessException.class, ()->service.updateRequest(19, requestDTO, principal));
    }
    //neprijavljen korisnik
    @Test
    @Transactional
    void test4(){
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(AuthenticationCredentialsNotFoundException.class, ()-> service.updateRequest(19, requestDTO, principal));
    }
}
