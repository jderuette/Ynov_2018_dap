package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dom
 *
 */
@Controller
public class Welcome {
    /**
     *
     */
    private static final int NB_MAILS = 10;

    /**
     * @param model .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    @RequestMapping("/")
    public String welcome(final ModelMap model) throws IOException, GeneralSecurityException {
        model.addAttribute("nbEmails", NB_MAILS);
        return "welcome";
    }
}
