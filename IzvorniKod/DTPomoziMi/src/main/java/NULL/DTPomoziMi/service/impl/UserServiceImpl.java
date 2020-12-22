package NULL.DTPomoziMi.service.impl;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.model.Role;
import NULL.DTPomoziMi.model.RoleEntity;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.repository.RatingRepo;
import NULL.DTPomoziMi.repository.RoleRepo;
import NULL.DTPomoziMi.repository.UserRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.UserPrincipalGetter;
import NULL.DTPomoziMi.web.DTO.RatingDTO;
import NULL.DTPomoziMi.web.DTO.UserRegisterDTO;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo; // UserDAO userDAO;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User registerUser(UserRegisterDTO user) {
//        if(userRepo.findByEmail(user.getEmail()) != null)
//            throw new UserAlreadyExistException("User with email address: " + user.getEmail() + " already exists!");

        User newUser = new User();

        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEnabled(true);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        RoleEntity roleEntity = roleRepo.findByRole(Role.ROLE_USER);
        if (roleEntity == null) {
            roleEntity = new RoleEntity();
            roleEntity.setRole(Role.ROLE_USER);
        }

        newUser.addRoleEntity(roleEntity);

        return userRepo.save(newUser);
    }

    @Override
    public User getUserByEmail(String email) {
        email = email == null ? null : email.trim();

        return userRepo.findByEmail(email);
    }

    @Override
    public User fetch(long id) {
        return userRepo.findById(id).orElseThrow(() -> new EntityMissingException(User.class, id));
    }

    
    
    @Override
    public User getUserByID(long ID) {
    	UserPrincipal userPrincipal = UserPrincipalGetter.getPrincipal();
    	
    	if(userPrincipal.getUser().getIdUser().equals(ID)) {
    		getUserWithIdEquals(ID);
    	}else if(userPrincipal.getUser().getEnumRoles().contains(Role.ROLE_ADMIN)){
    		getUserForAdminView(ID);
    	}else {
    		getUserForOtherUser(ID);
    	}
    	
        return fetch(ID);
    }

    private void getUserForOtherUser(long iD) {
		// TODO Auto-generated method stub
		
	}

	@Secured("ROLE_ADMIN")
    private void getUserForAdminView(long iD) {
		// TODO Auto-generated method stub
		
	}

	private void getUserWithIdEquals(long iD) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public Page<User> findUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public Set<Rating> findRatings(long id) {
        User r = userRepo.findById(id).get();
        return r.getRatedBy();
    }
    @Override
    public Rating createRating(RatingDTO ratingDTO) {
        Rating saved = ratingRepo.save(modelMapper.map(ratingDTO, Rating.class));
        return saved;
    }
}
