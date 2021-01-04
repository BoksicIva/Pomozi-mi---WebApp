package NULL.DTPomoziMi.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.UserPrincipalGetter;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class BlockUserTest {
    @Autowired
    private UserService service;

    private UserPrincipal principal;

    User u;
    //admin blokira korisnika
    @Test
    @WithUserDetails(value="jan.rocek@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test1() {
        principal = UserPrincipalGetter.getPrincipal();
        assertEquals(false, service.blockUnblockUser(12, false).getEnabled());
    }

    //korisnik pokusava blokirati drugog korisnika
    @Test
    @WithUserDetails(value="robert.dakovic@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void test2() {
        principal = UserPrincipalGetter.getPrincipal();
        u = new User();
        u = service.getUserByID(12l, principal);
        assertThrows(AccessDeniedException.class, ()->service.blockUnblockUser(12, false));
    }

    //neprijavljen korisnik
    @Test
    @Transactional
    void test3() {
        principal = UserPrincipalGetter.getPrincipal();
        assertThrows(AuthenticationCredentialsNotFoundException.class, ()->service.blockUnblockUser(12, false));
    }
}
