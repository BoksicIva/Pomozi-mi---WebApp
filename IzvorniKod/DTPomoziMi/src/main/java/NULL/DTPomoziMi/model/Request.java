package NULL.DTPomoziMi.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity(name = "Zahtjev")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Zahtjev")
    private Long id;

    @Column(name = "opis", nullable = false)
    private String description;

    @Column(nullable = true)
    private Date date;

    @Column(nullable = true)
    private Time time;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(nullable = true)
    private String phone;

    @Column(name = "ID_Autor", nullable = false)
    private Long id_Author;

    @Column(name = "ID_Izvrsitelj", nullable = true)
    private Long id_executor;

    @Transient
    private Location location;

    @Column(name = "duljina", nullable = true)
    private BigDecimal longitude;

    @Column(name = "sirina", nullable = true)
    private BigDecimal latitude;
}
