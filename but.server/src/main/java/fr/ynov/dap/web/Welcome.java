package fr.ynov.dap.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        model.addAttribute("fragment", "welcome");
        return "base";
    }

    /**
     * Logout of Dap.
     * @param request the HTTP request.
     * @return redirect home
     */
    @RequestMapping("/logout")
    public String logout(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
