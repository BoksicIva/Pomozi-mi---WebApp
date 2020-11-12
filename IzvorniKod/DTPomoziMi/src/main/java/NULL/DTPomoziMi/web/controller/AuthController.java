package NULL.DTPomoziMi.web.controller;

import NULL.DTPomoziMi.exception.UserAlreadyExistException;
import NULL.DTPomoziMi.jwt.JwtUtil;
import NULL.DTPomoziMi.properties.JwtConstants;
import NULL.DTPomoziMi.service.TokenService;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.CookieUtil;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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

    @PostMapping(value = "/registration", produces = {"application/json; charset=UTF-8"} )
    public ResponseEntity<?> register(@Valid UserDTO user, BindingResult bindingResult, HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            logger.debug("Binding errors {}", errors);

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        logger.debug("Registering user account with info: {}", user);

        try {
            userService.registerUser(user);
        }catch (DataIntegrityViolationException e){
            throw new UserAlreadyExistException("User with username: " + user.getEmail() + " already exists");
        }

        return ResponseEntity.ok(messageSource.getMessage("auth.registration.success", null, request.getLocale()));
    }

    @PostMapping(value = "/login", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password,
								   HttpServletResponse response) throws Exception {
        logger.debug("Login with username: {}", email);

        email = email == null ? null : email.trim();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            logger.debug("Incorect username: {} or password", email);
            return new ResponseEntity<String>("Incorect username or password", HttpStatus.UNAUTHORIZED); // TODO promjeni
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        String token = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        tokenService.updateToken(userDetails.getUsername(), refreshToken);

        CookieUtil.create(response, JwtConstants.JWT_COOKIE_NAME, token, false, -1);
        CookieUtil.create(response, JwtConstants.JWT_REFRESH_COOKIE_NAME, refreshToken, false, -1);

        return ResponseEntity.ok("User login successful!");
    }

}
