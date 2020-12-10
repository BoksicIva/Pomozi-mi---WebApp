package NULL.DTPomoziMi.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity(name = "Korisnik")
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_Korisnik")
    private Long id;
	
	@Column(name = "ime", nullable = false)
    private String firstName;
	
	@Column(name = "prezime", nullable = false)
    private String lastName;
	
	@Column(name = "lozinka", nullable = false)
    private String password;
	
	@Column(unique = true, nullable = false)
    private String email;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	  name = "imaulogu",
	  joinColumns = @JoinColumn(name = "ID_Korisnik"),
	  inverseJoinColumns = @JoinColumn(name = "ID_Uloga")
	)
    private Set<RoleEntity> roles = new HashSet<>();

	@Column(name = "aktivan")
    private boolean enabled;
	
	@Column(nullable = true)
    private String token;
    
    @Transient
	private Location location;
	
	@Column(name = "duljina", nullable = true)
    private BigDecimal longitude;
	
	@Column(name = "sirina", nullable = true)
    private BigDecimal latitude;

	public List<Role> getEnumRoles() {
		return roles.stream().map(r -> r.getRole()).collect(Collectors.toList());
	}
	
	public void addRole(RoleEntity role) {
		this.roles.add(role);
		role.getUsers().add(this);
	}
	public void removeRole(RoleEntity role) {
		this.roles.remove(role);
		role.getUsers().remove(this);
	}
}
