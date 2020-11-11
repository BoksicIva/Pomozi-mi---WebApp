package NULL.DTPomoziMi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class SecuredController {

    @Autowired
    MessageSource messageSource;

    @GetMapping("/notPermitted")
    public ResponseEntity<?> notPermitted(HttpServletRequest request){
        return ResponseEntity.ok(messageSource.getMessage("secured.not.permitted", null, request.getLocale()));
    }

    @PostMapping("/notPermitted")
    public ResponseEntity<?> notPostPermitted(HttpServletRequest request){
        return ResponseEntity.ok(messageSource.getMessage("secured.not.permitted", null, request.getLocale()));
    }

}
