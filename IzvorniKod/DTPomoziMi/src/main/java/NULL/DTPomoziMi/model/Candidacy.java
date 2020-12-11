package NULL.DTPomoziMi.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode.Exclude;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity(name = "kandidatura")
@Table(name = "kandidatura")
public class Candidacy implements Serializable {

	private static final long serialVersionUID = -363733843956508119L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_kandidatura")
	private Long IdCandidacy;

	@Column(name = "godina")
	private Integer year;

	@ManyToOne
	@JoinColumn(name = "id_lokacija")
	private Location location;

	@Exclude
	@ManyToMany(mappedBy = "candidacies")
	private Set<User> users = new HashSet<>();

}
