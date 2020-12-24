package NULL.DTPomoziMi.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.model.RequestStatus;
import NULL.DTPomoziMi.model.Role;
import NULL.DTPomoziMi.model.RoleEntity;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.repository.RoleRepo;
import NULL.DTPomoziMi.repository.UserRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.UserPrincipalGetter;
import NULL.DTPomoziMi.web.DTO.UserRegisterDTO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo; 

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(UserRegisterDTO user) {
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
	@PreAuthorize("isAuthenticated()")
	public User fetch(long id) {

		return userRepo.findById(id).orElseThrow(() -> new EntityMissingException(User.class, id));
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public User getUserByID(long ID) {
		UserPrincipal userPrincipal = UserPrincipalGetter.getPrincipal();

		User user = fetch(ID);
		if (
			!userPrincipal.getUser().getIdUser().equals(ID)
				&& !userPrincipal.getUser().getEnumRoles().contains(Role.ROLE_ADMIN)
		) user.setLocation(null);

		return user;
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public Page<User> findUsers(Pageable pageable, Specification<User> specification) {
		User user = UserPrincipalGetter.getPrincipal().getUser();

		Page<User> page = userRepo.findAll(specification, pageable);
		if (!user.getEnumRoles().contains(Role.ROLE_ADMIN)) page.forEach(u -> u.setLocation(null));

		return page;
	}

	@Override
	@Secured("ROLE_ADMIN")
	public User blockUser(long userID) { return userRepo.updateEnabled(userID, false); }

	@Override
	@Transactional
	@PreAuthorize("isAuthenticated()")
	public Map<String, Object> getStatistics(long idUser) {
		Map<String, Object> statistics = new HashMap<>(); // TODO tko moze vidjet?

		User user = fetch(idUser);
		statistics.put("numExecutedR", user.getExecutedReqs().size());
		statistics.put("numAuthoredR", user.getAuthoredReqs().size());
		statistics
			.put(
				"numFinalizedAR",
				user
					.getAuthoredReqs()
					.stream()
					.filter(r -> r.getStatus().equals(RequestStatus.FINALIZED))
					.count()
			);
		statistics
			.put(
				"numBlockedR",
				user
					.getAuthoredReqs()
					.stream()
					.filter(r -> r.getStatus().equals(RequestStatus.BLOCKED))
					.count()
			);
		double avgGrade = user.getRatedBy().stream().mapToInt(Rating::getRate).average().orElse(-1);
		statistics.put("avgGrade", (avgGrade == -1 ? null : avgGrade));
		return statistics;
	}
}
