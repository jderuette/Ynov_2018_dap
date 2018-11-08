package fr.ynov.dap.dap.web;

import fr.ynov.dap.dap.GMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller
public class Welcome {
    private final GMailService gMailService;

    @Autowired
    public Welcome(GMailService gMailService) {
        this.gMailService = gMailService;
    }

    @RequestMapping("/")
    public String WelcomeUser(ModelMap model) throws IOException, GeneralSecurityException {
//        String user = "pierre.plessy52@gmail.com";
//        model.addAttribute("nbEmails", gMailService.getNbUnreadEmails("foo").get("Unread").toString());
        return "welcome";
    }
}
