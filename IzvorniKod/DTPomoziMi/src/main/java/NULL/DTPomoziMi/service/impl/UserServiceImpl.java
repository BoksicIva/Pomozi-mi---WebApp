package NULL.DTPomoziMi.service.impl;

import NULL.DTPomoziMi.DAO.UserDAO;
import NULL.DTPomoziMi.MongoRepo.JwtModel;
import NULL.DTPomoziMi.MongoRepo.JwtMongoRepository;
import NULL.DTPomoziMi.exception.UserAlreadyExistException;
import NULL.DTPomoziMi.model.Role;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtMongoRepository jwtMongoRepository;

    @Override
    public User registerUser(UserDTO user) {
        if(userDao.getUserByEmail(user.getEmail()) != null)
            throw new UserAlreadyExistException("User with email address: " + user.getEmail() + " already exists!");

        User newUser = new User();

        newUser.setEmail(user.getEmail().trim());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getEmail().trim());
        newUser.setLastName(user.getLastName().trim());
        newUser.setRole(Role.ROLE_USER);
        newUser.setEnabled(true); // TODO enabled

        return  userDao.saveUser(newUser);

    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    @Transactional
    public void switchToken(String email, String token) {
        jwtMongoRepository.deleteById(email);

        JwtModel jwtModel = new JwtModel();
        jwtModel.setEmail(email);
        jwtModel.setToken(token);

        jwtMongoRepository.save(jwtModel);
    }

    @Override
    public void deleteTokenByUsername(String email){
        jwtMongoRepository.deleteById(email);
    }

}
