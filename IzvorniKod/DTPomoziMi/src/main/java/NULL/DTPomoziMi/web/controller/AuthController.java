package NULL.DTPomoziMi.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import NULL.DTPomoziMi.exception.UserAlreadyExistException;
import NULL.DTPomoziMi.jwt.JwtUtil;
import NULL.DTPomoziMi.properties.JwtConstants;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.TokenService;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.CookieUtil;
import NULL.DTPomoziMi.web.DTO.UserRegisterDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private JwtUtil jwtUtil;

	@Resource(name = "myUserDetailsService")
	private UserDetailsService userDetailsService;

	@PostMapping(value = "/registration", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> register(
		@Valid UserRegisterDTO user, BindingResult bindingResult, HttpServletRequest request
	) {

		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();
			logger.debug("Binding errors {}", errors);

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		logger.debug("Registering user account with info: {}", user);

		try {
			userService.registerUser(user);
		} catch (DataIntegrityViolationException e) {
			throw new UserAlreadyExistException(
				"User with username: " + user.getEmail() + " already exists"
			);
		}

		return ResponseEntity
			.ok(messageSource.getMessage("auth.registration.success", null, request.getLocale()));
	}

	@PostMapping(value = "/login", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<?> login(
		@RequestParam("email") String email, @RequestParam("password") String password,
		HttpServletResponse response
	) throws Exception {

		logger.debug("Login with username: {}", email);
		email = email == null ? null : email.trim();

		Authentication auth;
		try {
			auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (AuthenticationException e) {
			logger.debug("Incorect username: {} or password", email);
			return new ResponseEntity<String>(
				"Incorect username or password", HttpStatus.UNAUTHORIZED
			); // TODO promjeni
		}

		UserPrincipal user = (UserPrincipal) auth.getPrincipal();

		String refreshToken = jwtUtil.generateRefreshToken(user);
		tokenService.updateToken(refreshToken, user.getUsername());

		String token = jwtUtil.generateToken(user);

		CookieUtil.create(response, JwtConstants.JWT_COOKIE_NAME, token, false, -1);
		CookieUtil.create(response, JwtConstants.JWT_REFRESH_COOKIE_NAME, refreshToken, false, -1);

		return ResponseEntity.ok("User login successful!");
	}

}
