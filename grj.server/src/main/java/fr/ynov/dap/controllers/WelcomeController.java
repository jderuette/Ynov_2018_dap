package fr.ynov.dap.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @RequestMapping("/")
    public String Welcome(ModelMap model) {

        model.addAttribute("nbEmails", 2);

        return "Welcome";
    }

}
