package NULL.DTPomoziMi.service.impl;
import NULL.DTPomoziMi.DAO.UserDAO;
import NULL.DTPomoziMi.exception.UserAlreadyExistException;
import NULL.DTPomoziMi.model.Role;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.web.DTO.UserDTO;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserDTO user) {
        if(userDAO.getUserByEmail(user.getEmail()) != null)
            throw new UserAlreadyExistException("User with email address: " + user.getEmail() + " already exists!");

        User newUser = new User(
                user.getFirstName(),
                user.getLastName(),
                passwordEncoder.encode(user.getPassword()),
                user.getEmail(),
                Arrays.asList(Role.ROLE_USER),
                true,
                null,
                user.getLongitude(),
                user.getLatitude()
        );

        newUser = userDAO.saveUser(newUser);
        
        userDAO.saveRolesForUser(newUser.getId(), newUser.getRoles());
        
        return newUser;

    }

    @Override
    public User getUserByEmail(String email) {
        email = email == null ? null : email.trim();

        return userDAO.getUserByEmail(email);
    }

}
