package NULL.DTPomoziMi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.exception.IllegalAccessException;
import NULL.DTPomoziMi.model.Location;
import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.model.RequestStatus;
import NULL.DTPomoziMi.model.Role;
import NULL.DTPomoziMi.model.RoleEntity;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.repository.RoleRepo;
import NULL.DTPomoziMi.repository.UserRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.LocationService;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.UserPrincipalGetter;
import NULL.DTPomoziMi.web.DTO.LocationDTO;
import NULL.DTPomoziMi.web.DTO.RatingDTO;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import NULL.DTPomoziMi.web.DTO.UserRegisterDTO;
import NULL.DTPomoziMi.web.assemblers.RatingDTOAssembler;

@Service
public class UserServiceImpl implements UserService {

	private static String numExecutedRequests = "numExecutedR";
	private static String numAuthoredRequests = "numAuthoredR";
	private static String numFinalizedAuthoredRequests = "numFinalizedAR";
	private static String numBlockedRequests = "numBlockedR";

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private LocationService locationService;

	@Autowired
	private RatingDTOAssembler ratingDTOAssembler;

	@Autowired
	private ModelMapper modelMapper;

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
		statistics.put(numExecutedRequests, user.getExecutedReqs().size());
		statistics.put(numAuthoredRequests, user.getAuthoredReqs().size());
		statistics
			.put(
				numFinalizedAuthoredRequests,
				user
					.getAuthoredReqs()
					.stream()
					.filter(r -> r.getStatus().equals(RequestStatus.FINALIZED))
					.count()
			);
		statistics
			.put(
				numBlockedRequests,
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

	@Override
	public User updateUser(UserDTO userDTO, long id) {
		User user = UserPrincipalGetter.getPrincipal().getUser();

		if (!userDTO.getIdUser().equals(id))
			throw new IllegalArgumentException("User id must be preserved!");

		if (!user.getIdUser().equals(id))
			throw new IllegalAccessException("Missing permission to edit user's information!");

		modelMapper.map(userDTO, user);

		LocationDTO location = userDTO.getLocation();
		Location loc = resolveLocation(location);
		if (loc == null && location != null) {
			loc = modelMapper.map(location, Location.class);
			loc.setIdLocation(null);
		}
		user.setLocation(loc);

		return userRepo.save(user);
	}

	@Transactional
	@Override
	public List<RatingDTO> getChainOfTrust(long id) {
		User user = UserPrincipalGetter.getPrincipal().getUser(); // TODO ovo treba debelo testirat
		//da je korisnik kojeg ste vi visoko ocjenili ocijenio korisnika čiji profil gledate.
		return user
			.getRatedOthers()
			.stream()
			.filter(r -> r.getRate() > 3)
			.flatMap(
				r -> r
					.getRated()	
					.getRatedOthers()
					.stream()
					.filter(o -> o.getRated().getIdUser().equals(id))
			)
			.map(r -> ratingDTOAssembler.toModel(r))
			.collect(Collectors.toList());
	}

	private Location resolveLocation(LocationDTO dto) {
		if (dto != null) { // ako je dana lokacija onda provjeri postoji li vec spremljena pa ju dodaj u req ili... ako ne onda spremi i dodaj u req 
			try {
				Location loc = locationService
					.findByLatitudeAndLongitude(dto.getLatitude(), dto.getLongitude());

				if (loc == null) loc = locationService.save(dto);

				return loc;
			} catch (Exception e) {}
		}

		return null;
	}
}
