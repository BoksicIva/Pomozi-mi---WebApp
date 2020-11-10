package NULL.DTPomoziMi.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HTML5Controller {

    /*all paths that do not contain a period (and are not explicitly mapped already)
    are React routes, and should forward to the home page:
    */
    @RequestMapping(value = "/{[path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

    @GetMapping("/getCsrf") // TODO makni
    public ResponseEntity<?> get(){
        return ResponseEntity.ok("");
    }

}
