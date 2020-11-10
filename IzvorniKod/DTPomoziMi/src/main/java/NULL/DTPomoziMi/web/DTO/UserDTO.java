package NULL.DTPomoziMi.web.DTO;

import NULL.DTPomoziMi.validation.MatchPassword;
import NULL.DTPomoziMi.validation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@MatchPassword(message = "{UserDTO.MatchPassword}")
public class UserDTO {
    @NotNull
    @Size(min=1, message = "{Size.UserDTO.firstName}")
    private String firstName;

    @NotNull
    @Size(message = "{Size.UserDTO.lastName}", min=1)
    private String lastName;

    @ValidPassword
    private String password;
    private String secondPassword;

    @NotNull
    @Email(message = "{UserDTO.email}")
    private String email;

    // TODO validacija duljine i sirine...
    private BigDecimal longitude;
    private BigDecimal latitude;

    public UserDTO() {
        super();
    }

    public UserDTO(String firstName,  String lastName, String password, String secondPassword, String email, BigDecimal longitude, BigDecimal latitude) {
        setFirstName(firstName);
        setLastName(lastName);
        this.password = password;
        this.secondPassword = secondPassword;
        setEmail(email);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        firstName = firstName == null ? null : firstName.trim();
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        lastName = lastName == null ? null : lastName.trim();
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email == null ? null : email.trim();
        this.email = email;
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
