package NULL.DTPomoziMi.service;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.web.DTO.UserDTO;

public interface UserService {

    User registerUser(UserDTO user);

    User getUserByEmail(String email);

}
