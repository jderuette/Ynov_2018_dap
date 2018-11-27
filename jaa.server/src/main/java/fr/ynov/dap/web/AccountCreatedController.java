package fr.ynov.dap.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Account created controller.
 */
@Controller
public class AccountCreatedController {
    /**
     * Account created page. You will be redirected to that page if the creation of the account succeeded.
     * @param model model to send to the view.
     * @return the name of the template to display.
     */
    @RequestMapping("/accountAdded")
    public String welcome(final ModelMap model) {
        model.addAttribute("fragment", "fragments/accountAddedFragment");
        return "base";
    }
}
