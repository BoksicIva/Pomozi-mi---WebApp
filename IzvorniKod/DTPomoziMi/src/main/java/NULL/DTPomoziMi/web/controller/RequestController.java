package NULL.DTPomoziMi.web.controller;

import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.service.RequestService;
import NULL.DTPomoziMi.web.DTO.RequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")

public class RequestController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RequestService requestService;

    @Autowired
    private MessageSource messageSource;

    @PreAuthorize( value = "isAuthenticated()")

    @PostMapping(value = "/request", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> makeRequest(RequestDTO requestDTO, BindingResult bindingResult, HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            logger.debug("Binding errors {}", errors);

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        logger.debug("{}", requestDTO);

        requestService.createRequest(requestDTO);

        return ResponseEntity.ok(messageSource.getMessage("auth.request.success", null, request.getLocale()));
    }

    @GetMapping("/request")
    List<Request> all() {
        return requestService.findAll();
    }
}
