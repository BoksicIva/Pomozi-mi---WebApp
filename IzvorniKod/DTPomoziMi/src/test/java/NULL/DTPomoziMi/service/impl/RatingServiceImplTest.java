package NULL.DTPomoziMi.service.impl;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.exception.IllegalActionException;
import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.repository.RatingRepo;
import NULL.DTPomoziMi.repository.RequestRepo;
import NULL.DTPomoziMi.repository.UserRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.web.DTO.RatingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class RatingServiceImplTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RatingRepo ratingRepo;

    RatingServiceImpl service;
    private UserPrincipal principal;
    private Rating r;
    private RatingDTO ratingDTO;

    @BeforeEach
    void setUp()
    {
        service = new RatingServiceImpl();
        r = new Rating();
        r.setRate(3);
        r.setIdRating(3l);
        principal = new UserPrincipal(userRepo.findById(11l).get());
    }
    //dohvat postojeceg ratinga
    @Test
    void fetch1()
    {
        assertEquals(r, service.fetch(3l));
    }
    //pokusava dohvatiti nepostojeci id
    @Test
    void fetch2()
    {
        assertThrows(new EntityMissingException(r.getClass(), 300l).getClass(), ()->service.fetch(300l));
    }
    //uspjesno ocjenjivanje
    @Test
    void createTest1()
    {
        //TODO: u bazi: staviti u request1 da je Marija executor i status izvrsen
        assertEquals(r, service.create(ratingDTO, 11l, 1l, principal));
    }
    //nije autor niti izvrsitelj zahtjeva
    @Test
    void create2()
    {
        assertThrows(IllegalAccessException.class, ()->service.create(ratingDTO, 11l, 3l, principal));
    }
    //ocjenjuje se neizvrsen zahtjev
    @Test
    void create3()
    {
        //TODO: u bazi: Marija izvrsava zahtjev(id:9), jos nije oznacen izvrsen
        assertThrows(IllegalActionException.class, ()->service.create(ratingDTO, 11l, 9l, principal));
    }
}