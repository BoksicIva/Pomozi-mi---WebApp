package NULL.DTPomoziMi.service.impl;

import NULL.DTPomoziMi.exception.IllegalActionException;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.repository.UserRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import NULL.DTPomoziMi.web.DTO.UserRegisterDTO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    private UserServiceImpl service;

    private UserPrincipal principal;
    private User expectedUser = new User();
    private UserDTO userDTO;
    @Before
    void setup(){
        service = new UserServiceImpl();
        expectedUser = userRepo.findByEmail("marija.orec@gmail.com");
        principal = new UserPrincipal(expectedUser);
        userDTO = new UserDTO();
        modelMapper.map(userDTO, expectedUser);
        //TODO: povezati dto sa user
    }
    //provjera registracije novog korisnika
    @Test
    void registerUserTest1()
    {
        UserRegisterDTO user = new UserRegisterDTO();
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