package NULL.DTPomoziMi.service.impl;

import NULL.DTPomoziMi.exception.IllegalActionException;
import NULL.DTPomoziMi.model.RequestStatus;
import NULL.DTPomoziMi.model.Role;
import NULL.DTPomoziMi.model.RoleEntity;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.repository.RequestRepo;
import NULL.DTPomoziMi.repository.UserRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class RequestServiceImplTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RequestRepo requestRepo;

    private RequestServiceImpl service;
    private UserPrincipal principal;
    private User user = new User();
    private UserPrincipal principalAdmin;
    private User admin = new User();
    private long id_req;

    @Before
    void setup()
    {
        service = new RequestServiceImpl();
        user = userRepo.findByEmail("marija.orec@gmail.com");
        principal = new UserPrincipal(user);
        //dodati ulogu admina ili iz baze dohvatiti admina
        //admin.addRoleEntity(Role.ROLE_ADMIN);
        principalAdmin = new UserPrincipal(admin);
        id_req = 39l;
    }
    //brisanje vlastitog zahtjeva
    @Test
    void deleteRequestTest1(){
        assertEquals(RequestStatus.DELETED, service.deleteRequest(id_req, principal).getStatus());
    }
    //admin brise zahtjev
    @Test
    void deleteRequestTest2(){
        assertEquals(RequestStatus.DELETED, service.deleteRequest(id_req, principalAdmin).getStatus());
    }
    //nevlasteno brisanje tudeg zahtjeva
    @Test
    void deleteRequestTest3(){
        long new_id_req = 9l;
        assertThrows(IllegalAccessException.class, ()->service.deleteRequest(new_id_req, principal).getStatus());
    }
    //Oznaka rjesavanja aktivnog zahtjeva
    @Test
    void pickForExecutionTest1() {
        long new_id_req = 3l;
        assertEquals(RequestStatus.EXECUTING, service.pickForExecution(new_id_req, principal).getStatus());
    }
    //Oznaka rjesavanja neaktivnog zahtjeva
    @Test
    void pickForExecutionTest2() {
        long new_id_req = 3l;
        assertThrows(IllegalActionException.class, ()->service.pickForExecution(new_id_req, principal).getStatus());
    }
}