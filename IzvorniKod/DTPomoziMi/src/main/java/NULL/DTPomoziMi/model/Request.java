package NULL.DTPomoziMi.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "zahtjev")
@Entity(name = "zahtjev")
public class Request implements Serializable {

	private static final long serialVersionUID = 1835469530725494808L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_zahtjev")
	private Long IdRequest;

	@Column(name = "brojmobitela")
	private String phone;

	@Column(name = "datum")
	private Date date;

	@Column(name = "izvrsen")
	private Boolean executed;

	@Column(name = "opis")
	private String description;

	@Column(name = "primljenanotif")
	private Boolean recivedNotif;

	@Enumerated(EnumType.STRING)
	private RequestStatus status;

	@Column(name = "vrijeme")
	private Time time;

	@Exclude
	@OneToMany(mappedBy = "request")
	private Set<Rating> ratings = new HashSet<>();

	@Exclude
	@ManyToOne
	@JoinColumn(name = "id_autor")
	private User author;

	@Exclude
	@ManyToOne
	@JoinColumn(name = "id_izvrsitelj")
	private User executor;

	@Exclude
	@ManyToOne
	@JoinColumn(name = "id_lokacija")
	private Location location;

	public Rating addRating(Rating rating) {
		getRatings().add(rating);
		rating.setRequest(this);

		return rating;
	}

	public Rating removeRating(Rating rating) {
		getRatings().remove(rating);
		rating.setRequest(null);

		return rating;
	}
}
