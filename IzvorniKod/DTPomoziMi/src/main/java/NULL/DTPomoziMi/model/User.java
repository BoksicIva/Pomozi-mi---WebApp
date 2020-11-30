package NULL.DTPomoziMi.model;

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
	
	public User() {}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<RoleEntity> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}
	public List<Role> getEnumRoles() {
		return roles.stream().map(r -> r.getRole()).collect(Collectors.toList());
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
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
