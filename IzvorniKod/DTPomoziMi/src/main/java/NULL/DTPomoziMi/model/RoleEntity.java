package NULL.DTPomoziMi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name = "Uloga")
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Uloga")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "naziv", unique = true)
	private Role role;
	
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(
//	  name = "ImaUlogu",
//	  inverseJoinColumns = @JoinColumn(name = "ID_Korisnik"),
//	  joinColumns = @JoinColumn(name = "ID_Uloga")
//	)
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		this.users.add(user);
		user.getRoles().add(this);
	}
	public void removeUser(User user) {
		this.users.remove(user);
		user.getRoles().remove(this);
	}
	
}
