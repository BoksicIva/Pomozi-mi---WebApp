package NULL.DTPomoziMi.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.util.UserPrincipalGetter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import NULL.DTPomoziMi.repository.UserRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.UserService;

import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")

class GetUserByIdTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService service;

    private UserPrincipal principal;

    //dobar id
    @Test
    @WithUserDetails(value="jan.rocek@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test1()
    {
        principal = UserPrincipalGetter.getPrincipal();
        assertEquals("jan.rocek@gmail.com", service.getUserByID(3l, principal).getEmail());
    }

    //nepostojeci id
    @Test
    @WithUserDetails(value="jan.rocek@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test2()
    {
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(EntityMissingException.class, ()->service.getUserByID(110l, principal));
    }

    //neprijavljen korisnik
    @Test
    void test3()
    {
        assertThrows(AuthenticationCredentialsNotFoundException.class, ()->service.getUserByID(110l, principal));
    }

}
