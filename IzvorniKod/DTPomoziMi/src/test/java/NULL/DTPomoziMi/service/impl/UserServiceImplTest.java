package NULL.DTPomoziMi.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import NULL.DTPomoziMi.exception.IllegalActionException;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.repository.UserRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.UserPrincipalGetter;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import NULL.DTPomoziMi.web.DTO.UserRegisterDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
class UserServiceImplTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService service;

    private UserPrincipal principal;
    private User expectedUser = new User();
    private UserDTO userDTO;
    
	/*
	 * @Before void setup(){ service = new UserServiceImpl(); expectedUser =
	 * userRepo.findByEmail("marija.orec@gmail.com"); principal = new
	 * UserPrincipal(expectedUser); userDTO = new UserDTO();
	 * modelMapper.map(userDTO, expectedUser); //TODO: povezati dto sa user }
	 */
    
    //provjera registracije novog korisnika
    @Test
    @WithUserDetails(value="jan.rocek@gmail.com", userDetailsServiceBeanName="myUserDetailsService")
    @Transactional
    void registerUserTest1()
    {
        UserRegisterDTO user = new UserRegisterDTO();
        UserPrincipal principal = UserPrincipalGetter.getPrincipal();
        System.out.println(service.fetch(3).getIdUser());
        user.setFirstName("Ana");
        user.setLastName("Anic");
        user.setPassword("ana123");
        user.setEmail("ana.anic@gmail.com");
        assertEquals(user.getEmail(), service.registerUser(user).getEmail());
    }
    //provjera registracije korisnika sa postojecim emailom
    @Test
    void registerUserTest2()
    {
        UserRegisterDTO user = new UserRegisterDTO();
        user.setFirstName("Marija");
        user.setLastName("Orec");
        user.setPassword("MarijaOrec1@");
        user.setEmail("marija.orec@gmail.com");
        assertThrows(Exception.class, ()->service.registerUser(user));
    }
    //provjera dohvata s postojecim ID
    @Test
    void getUserByIDTest1()
    {
        assertEquals(expectedUser, service.getUserByID(11l, principal));
    }
    //provjera dohvata sa nepostojecim ID
    @Test
    void getUserByIDTest2()
    {
        assertThrows(IllegalActionException.class, ()->service.getUserByID(110l, principal));
    }
    //korisnik ima pristup uredivanju profila
    @Test
    void updateUserTest1()
    {
        assertEquals(expectedUser, service.updateUser(userDTO, 11l, principal));
    }
    //korisnik nema pristup uredivanju profila
    @Test
    void updateUserTest2()
    {
        assertThrows(IllegalArgumentException.class, ()->service.updateUser(userDTO, 1l, principal));
    }
}