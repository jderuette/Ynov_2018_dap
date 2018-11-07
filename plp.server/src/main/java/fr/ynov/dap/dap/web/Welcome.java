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
    @Autowired
    private GMailService gMailService;

    @RequestMapping("/")
    public String Welcome(ModelMap model) throws IOException, GeneralSecurityException {
        String user = "pierre.plessy52@gmail.com";
        model.addAttribute("nbEmails", gMailService.getNbUnreadEmails(user).get("Unread"));
        return "Welcome";
    }
}
