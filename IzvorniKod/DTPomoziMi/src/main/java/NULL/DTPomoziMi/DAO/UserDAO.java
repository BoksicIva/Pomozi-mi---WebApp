package NULL.DTPomoziMi.DAO;

import java.util.List;

import NULL.DTPomoziMi.model.Role;
import NULL.DTPomoziMi.model.User;

public interface UserDAO {

    User saveUser(User user);
    User getUserByEmail(String email);
    void saveRolesForUser(Long userId, List<Role> roles);

}
