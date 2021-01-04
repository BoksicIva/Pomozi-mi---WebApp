package NULL.DTPomoziMi.service.impl;

import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.UserPrincipalGetter;
import NULL.DTPomoziMi.web.DTO.UserDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")

public class UpdateUserTest {

    @Autowired
    private UserService service;

    private UserPrincipal principal;

    private UserDTO userDTO;

    @BeforeEach
    void setup(){
        userDTO = new UserDTO();
        userDTO.setIdUser(11l);
        userDTO.setFirstName("Marija");
        userDTO.setLastName("Oreč");
        userDTO.setEmail("marija.orec@gmail.com");
    }

    //korisnik ima pristup uredivanju profila
    @Test
    @WithUserDetails(value="marija.orec@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test1()
    {
        principal = UserPrincipalGetter.getPrincipal();
        assertEquals(principal.getUser(), service.updateUser(userDTO, 11l, principal));
    }

    //korisnik nema pristup uredivanju profila
    @Test
    @WithUserDetails(value="matea.lipovac@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test2()
    {
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(IllegalAccessException.class, ()->service.updateUser(userDTO, 11l, principal));
    }
    //userDTO i id se ne poklapaju
    @Test
    @WithUserDetails(value="marija.orec@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test3()
    {
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(IllegalArgumentException.class, ()->service.updateUser(userDTO, 3l, principal));
    }

    //neprijavljen korisnik
    @Test
    void test4()
    {
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(AuthenticationCredentialsNotFoundException.class, ()->service.updateUser(userDTO, 11l, principal));
    }
}
