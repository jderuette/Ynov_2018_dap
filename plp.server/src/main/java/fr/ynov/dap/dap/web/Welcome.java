package fr.ynov.dap.dap.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Welcome {
    /**
     *
     * @return a template
     */
    @RequestMapping("/")
    public String WelcomeUser() {
        return "welcome";
    }
}
