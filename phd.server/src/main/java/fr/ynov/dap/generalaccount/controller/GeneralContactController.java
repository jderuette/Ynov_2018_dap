package fr.ynov.dap.generalaccount.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.generalaccount.service.GeneralContactService;

/**
 *
 * @author Dom
 *
 */
@Controller
public class GeneralContactController {

    /**
    *
    */
    @Autowired
    private GeneralContactService generalContactService;

    /**
    *
    * @param model .
    * @return .
    * @throws GeneralSecurityException .
    * @throws IOException .
    * @param userId .
    */
    @RequestMapping("/generalContact")
    public String generalMail(final Model model, @RequestParam("userId") final String userId)
            throws IOException, GeneralSecurityException {
        int nbGeneralContact = generalContactService.getTotalContactGoogleAndMicrosoft(userId);
        model.addAttribute("nbGeneralContact", nbGeneralContact);
        return "generalContact";
    }
}
