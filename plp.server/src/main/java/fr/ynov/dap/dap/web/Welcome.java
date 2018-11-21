package fr.ynov.dap.dap.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Welcome {

    @RequestMapping("/")
    public String WelcomeUser() {
        return "welcome";
    }
}
