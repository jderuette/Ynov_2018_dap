package fr.ynov.dap.generalaccount.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.generalaccount.service.GeneralMailService;

/**
 *
 * @author Dom
 *
 */
@Controller
public class GeneralMailController {

    /**
     *
     */
    @Autowired
    private GeneralMailService generalMailService;

    /**
     *
     * @param model .
     * @return .
     * @throws GeneralSecurityException .
     * @throws IOException .
     * @param userId .
     */
    @RequestMapping("/generalMail")
    public String generalMail(final Model model, @RequestParam("userId") final String userId)
            throws IOException, GeneralSecurityException {
        int nbGeneralMail = generalMailService.getTotalMailGoogleAndMicrosoft(userId);
        model.addAttribute("nbGeneralMail", nbGeneralMail);
        return "generalmail";
    }
}
