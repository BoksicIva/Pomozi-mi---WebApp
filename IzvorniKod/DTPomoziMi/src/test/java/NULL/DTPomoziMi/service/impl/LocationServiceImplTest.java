package NULL.DTPomoziMi.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import NULL.DTPomoziMi.repository.LocationRepo;

class LocationServiceImplTest {

    @Autowired
    LocationRepo locationRepo;

    private LocationServiceImpl service;

    @Before
    void setup()
    {
        service = new LocationServiceImpl();
    }

    //dobro zadane koordinate
    @Test
    void findByLatitudeAndLongitudeTest1()
    {
        BigDecimal longitude = new BigDecimal(16.440194);
        BigDecimal latitude = new BigDecimal(43.508132);
        assertEquals("Split", service.findByLatitudeAndLongitude(latitude, longitude).getTown());
    }
    //krivo zadane koordinate(negativne)
    @Test
    void findByLatitudeAndLongitudeTest2()
    {
        BigDecimal longitude = new BigDecimal(5.440194);
        BigDecimal latitude = new BigDecimal(-3.508132);
        assertNotEquals("Split", service.findByLatitudeAndLongitude(latitude, longitude).getTown());
    }
}