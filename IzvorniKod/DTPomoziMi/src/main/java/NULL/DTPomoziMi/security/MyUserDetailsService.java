package NULL.DTPomoziMi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;//UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		try {
			User user = userRepo.findByEmail(email);
			if (user == null)
				throw new UsernameNotFoundException(
					"User with username: " + email + " was not found."
				);

			return new UserPrincipal(user);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
