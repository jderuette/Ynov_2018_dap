package fr.ynov.dap.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for Welcome page.
 * @author thibault
 *
 */
@Controller
public class Welcome {
    /**
     * Route welcome.
     * @param model Model data for View
     * @return template name
     */
    @RequestMapping("/")
    public String welcome(final ModelMap model) {
        final int nbunreadEmails = 5;
        model.addAttribute("nbEmails", nbunreadEmails);
        return "welcome";
    }
}
