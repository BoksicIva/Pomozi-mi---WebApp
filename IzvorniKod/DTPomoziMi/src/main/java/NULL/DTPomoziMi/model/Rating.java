package NULL.DTPomoziMi.model;

import lombok.*;

import java.io.Serializable;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity(name = "ocjenjivanje")
@Table(name = "ocjenjivanje")
public class Rating implements Serializable {

	private static final long serialVersionUID = -3163979796749734017L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ocjenjivanje")
	private Long IdRating;

	@Column(name = "komentar")
	private String comment;

	@Column(name = "ocjena")
	private Integer rate;

	@ManyToOne
	@JoinColumn(name = "id_ocjenjeni")
	private User rated;

	@ManyToOne
	@JoinColumn(name = "id_ocjenjivac")
	private User rator;

	@ManyToOne
	@JoinColumn(name = "id_zahtjev")
	private Request request;

}
