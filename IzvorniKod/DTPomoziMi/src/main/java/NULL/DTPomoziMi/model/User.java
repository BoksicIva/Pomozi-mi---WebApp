package NULL.DTPomoziMi.model;

import java.math.BigDecimal;
import java.util.List;

public class User{

    private Long id;
    private String firstName;
    private String lastName;
    private String password; // TODO length password encoder u bazi...
    private String email;
    private List<Role> roles;
    private boolean enabled;
    private String token;
	private Location location;
    private BigDecimal longitude; //dužina
    private BigDecimal latitude; //širina

    
    
	public User(Long id, String firstName, String lastName, String password, String email, List<Role> roles,
			boolean enabled, String token, BigDecimal longitude, BigDecimal latitude) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.roles = roles;
		this.enabled = enabled;
		this.token = token;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public User(String firstName, String lastName, String password, String email, List<Role> roles, boolean enabled,
			String token, BigDecimal longitude, BigDecimal latitude) {
		
		this(null, firstName, lastName, password, email, roles, enabled, token, longitude, latitude);
		
	}
	
	public User(User user) {
		this(user.id, user.firstName, user.lastName, user.password, user.email, user.roles,
				user.enabled, user.token, user.longitude, user.latitude);
	}
	
	

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
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
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

    
}
