package NULL.DTPomoziMi.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/notPermitted")
    public String notPermitted(){
        return "Restricted access point reached!";
    }

    @PostMapping("/notPermitted")
    public String notPostPermitted(){
        return "Restricted access point reached!";
    }
}
