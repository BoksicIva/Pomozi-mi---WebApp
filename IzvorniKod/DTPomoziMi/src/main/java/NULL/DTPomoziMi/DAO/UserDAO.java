package NULL.DTPomoziMi.DAO;

import NULL.DTPomoziMi.model.User;

public interface UserDAO {

    User saveUser(User user);
    User getUserByEmail(String email);

}
