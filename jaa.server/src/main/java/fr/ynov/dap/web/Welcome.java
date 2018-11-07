package fr.ynov.dap.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.services.GMailService;

/**
 * @author adrij
 *
 */
@Controller
public class Welcome {
    @Autowired
    private GMailService service;
    
    /**
     * 
     */
    public Welcome() {
    }
    
    @RequestMapping("/")
    public String welcome(ModelMap model) throws Exception {
        Integer nbunreadEmails = service.getUnreadEmailsNumber("me", "adrien");
        model.addAttribute("nbEmails", nbunreadEmails);
        return "welcome";
    }

}
