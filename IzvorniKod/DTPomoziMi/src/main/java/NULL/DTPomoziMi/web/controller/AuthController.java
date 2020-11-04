package NULL.DTPomoziMi.web.controller;

import NULL.DTPomoziMi.jwt.JwtUtil;
import NULL.DTPomoziMi.properties.Constants;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.util.CookieUtil;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@RequestMapping("/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private JwtUtil jwtUtil;

    @Resource(name = "myUserDetailsService")
    private UserDetailsService userDetailsService;

    @GetMapping("/getCsrf") // TODO makni
    public ResponseEntity<?> get(){
        return ResponseEntity.ok("evo");
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid UserDTO user, BindingResult bindingResult, HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            logger.debug("Binding errors {}", errors);

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        logger.debug("Registering user account with info: {}", user);

        userService.registerUser(user);

        //TODO registraton
        return ResponseEntity.ok(messageSource.getMessage("auth.registration.success", null,"Registration successful", request.getLocale()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password,
								   HttpServletResponse response) throws Exception {

        logger.debug("Login with username: {}", email);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<String>("Incorect username or password", HttpStatus.UNAUTHORIZED); // TODO promjeni
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        String token = jwtUtil.generateToken(userDetails);

        userService.switchToken(userDetails.getUsername(), token);

        CookieUtil.create(response, Constants.JWT_COOKIE_NAME, token, false,-1);

        return ResponseEntity.ok("User login successful!");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.clear(response, Constants.JWT_COOKIE_NAME);

        String token = CookieUtil.getValue(request, Constants.JWT_COOKIE_NAME);
        String username = jwtUtil.extractUsername(token);

        userService.deleteTokenByUsername(username);

        return ResponseEntity.ok().build();
    }

}
