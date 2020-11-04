package NULL.DTPomoziMi.web.DTO;

import NULL.DTPomoziMi.validation.MatchPassword;
import NULL.DTPomoziMi.validation.ValidPassword;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MatchPassword(message = "{UserDTO.MatchPassword}")
public class UserDTO {
    @NotNull
    @Size(min=1, message = "{Size.UserDTO.firstName}")
    private String firstName;

    @NotNull
    @Size(min=1, message = "{Size.UserDTO.lastName}")
    private String lastName;

    @ValidPassword
    private String password;

    private String secondPassword;

    // TODO valid email i vracanje validacijskih errora
    @NotNull
    @Size(min = 1, message = "{Size.userDTO.email}")
    private String email;

    private Integer role;

    public UserDTO() {
        super();
    }

    public UserDTO(String firstName, String lastName, String password, String secondPassword, String email, Integer role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.secondPassword = secondPassword;
        this.email = email;
        this.role = role;
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
        this.email = email;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", secondPassword='" + secondPassword + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
