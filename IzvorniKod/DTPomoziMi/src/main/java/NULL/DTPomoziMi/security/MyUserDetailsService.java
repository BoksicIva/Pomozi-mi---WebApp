package NULL.DTPomoziMi.security;

import NULL.DTPomoziMi.DAO.UserDAO;
import NULL.DTPomoziMi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        try {
            User user = userDAO.getUserByEmail(email);
            if (user == null)
                throw new UsernameNotFoundException("User with username: " + email + " was not found.");

            return new UserPrincipal(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
