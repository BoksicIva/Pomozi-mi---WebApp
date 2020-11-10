package NULL.DTPomoziMi.exception.handling;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import NULL.DTPomoziMi.exception.UserAlreadyExistException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicateKeyException(DuplicateKeyException e){
        e.printStackTrace();

        Map<String, String> props = new HashMap<>();
        props.put("message", e.getMessage());
        props.put("status", String.valueOf(HttpStatus.FORBIDDEN.value()));
        props.put("error", "Duplicate key");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException e){
        e.printStackTrace();

        Map<String, String> props = new HashMap<>();
        props.put("message", e.getMessage());
        props.put("status", String.valueOf(HttpStatus.FORBIDDEN.value()));
        props.put("error", "User already exists");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handleUsernameNotFoundException(UsernameNotFoundException e){
        e.printStackTrace();

        Map<String, String> props = new HashMap<>();
        props.put("message", e.getMessage());
        props.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        props.put("error", "User not found");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        e.printStackTrace();

        Map<String, String> props = new HashMap<>();
        props.put("message", e.getMessage());
        props.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        props.put("error", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
