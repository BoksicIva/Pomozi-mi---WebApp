package NULL.DTPomoziMi.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Entity(name = "korisnik")
@Table(name = "korisnik")
public class User implements Serializable {
	private static final long serialVersionUID = -7095903751090463181L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_korisnik")
	private Long IdUser;

	@Column(name = "aktivan")
	private Boolean enabled;

	private String email;

	@Column(name = "ime")
	private String firstName;

	@Column(name = "lozinka")
	private String password;

	@Column(name = "prezime")
	private String lastName;

	private String token;

	@Exclude
	@ManyToMany
	@JoinTable(
			name = "kandidiranje", joinColumns = { @JoinColumn(name = "id_korisnik") },
			inverseJoinColumns = { @JoinColumn(name = "id_kandidatura") }
	)
	private Set<Candidacy> candidacies = new HashSet<>();

	@Exclude
	@ManyToOne
	@JoinColumn(name = "id_lokacija")
	private Location location;

	@Exclude
	@OneToMany(mappedBy = "rated")
	private Set<Rating> ratedBy = new HashSet<>();

	@Exclude
	@OneToMany(mappedBy = "rator")
	private Set<Rating> ratedOthers = new HashSet<>();

	@Exclude
	@ManyToMany(mappedBy = "users")
	private Set<RoleEntity> roles = new HashSet<>();

	@Exclude
	@OneToMany(mappedBy = "author")
	private Set<Request> authoredReqs = new HashSet<>();

	@Exclude
	@OneToMany(mappedBy = "executor")
	private Set<Request> executedReqs = new HashSet<>();

	public List<Role> getEnumRoles() {
		return roles.stream().map(r -> r.getRole()).collect(Collectors.toList());
	}

	public Rating addRatedBy(Rating ratedBy) {
		getRatedBy().add(ratedBy);
		ratedBy.setRated(this);

		return ratedBy;
	}

	public Rating removeRatedBy(Rating ratedBy) {
		getRatedBy().remove(ratedBy);
		ratedBy.setRated(null);

		return ratedBy;
	}

	public Rating addRatedOther(Rating ratedOther) {
		getRatedOthers().add(ratedOther);
		ratedOther.setRator(this);

		return ratedOther;
	}

	public Rating removeRatedOther(Rating ratedOther) {
		getRatedOthers().remove(ratedOther);
		ratedOther.setRator(null);

		return ratedOther;
	}

	public Request addAuthoredReq(Request authoredReq) {
		getAuthoredReqs().add(authoredReq);
		authoredReq.setAuthor(this);

		return authoredReq;
	}

	public Request removeAuthoredReq(Request authoredReq) {
		getAuthoredReqs().remove(authoredReq);
		authoredReq.setAuthor(null);

		return authoredReq;
	}

	public Request addExectedReq(Request exectedReq) {
		getExecutedReqs().add(exectedReq);
		exectedReq.setExecutor(this);

		return exectedReq;
	}

	public Request removeExectedReq(Request exectedReq) {
		getExecutedReqs().remove(exectedReq);
		exectedReq.setExecutor(null);

		return exectedReq;
	}

	public RoleEntity addRoleEntity(RoleEntity roleEntity) {
		getRoles().add(roleEntity);
		roleEntity.getUsers().add(this);

		return roleEntity;
	}

	public RoleEntity removeRoleEntity(RoleEntity roleEntity) {
		getRoles().remove(roleEntity);
		roleEntity.getUsers().remove(null);

		return roleEntity;
	}

	public Candidacy addCandidacy(Candidacy candidacy) {
		getCandidacies().add(candidacy);
		candidacy.getUsers().add(this);

		return candidacy;
	}

	public Candidacy removeCandidacy(Candidacy candidacy) {
		getCandidacies().remove(candidacy);
		candidacy.getUsers().remove(null);

		return candidacy;
	}

}
