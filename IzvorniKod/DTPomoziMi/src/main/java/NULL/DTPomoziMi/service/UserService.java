package NULL.DTPomoziMi.service;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User registerUser(UserDTO user);

    User getUserByEmail(String email);

    Page<User> findUsers(Pageable pageable); // TODO prosiri parametre svime sta zelis... a ne to u controlleru radit

}
