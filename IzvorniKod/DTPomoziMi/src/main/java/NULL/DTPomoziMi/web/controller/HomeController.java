package NULL.DTPomoziMi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    @ResponseBody
    @GetMapping("/api/notPermitted")
    public String notPermitted(){
        return "Restricted access point reached!";
    }

    @ResponseBody
    @PostMapping("/api/notPermitted")
    public String notPostPermitted(){
        return "Restricted access point reached!";
    }
}
