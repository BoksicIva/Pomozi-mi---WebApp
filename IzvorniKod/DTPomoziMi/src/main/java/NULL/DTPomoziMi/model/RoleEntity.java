package NULL.DTPomoziMi.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "Uloga")
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_Uloga")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "naziv", unique = true)
	private Role role;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	  name = "ImaUlogu",
	  inverseJoinColumns = @JoinColumn(name = "ID_Korisnik"),
	  joinColumns = @JoinColumn(name = "ID_Uloga")
	)
    private List<User> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
