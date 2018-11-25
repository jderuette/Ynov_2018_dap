package fr.ynov.dap.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.services.google.GMailService;

/**
 * @author adrij
 *
 */
@Controller
public class Welcome {
    /**
     * instantiate the GMailService using the injection of dependency.
     */
    @Autowired
    private GMailService service;

    /**
     * default constructor.
     */
    public Welcome() {
    }

    /**
     * root page.
     * @param model model to send to the view.
     * @return the name of the template to display.
     * @throws Exception exception
     */
    @RequestMapping("/")
    public String welcome(final ModelMap model) throws Exception {
        Integer nbunreadEmails = service.getUnreadEmailsNumber("me", "adrien");
        model.addAttribute("nbEmails", nbunreadEmails);
        return "welcome";
    }

}
