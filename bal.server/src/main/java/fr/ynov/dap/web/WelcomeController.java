package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.exception.UserException;
import fr.ynov.dap.google.GMailService;

/**
 * The Class WelcomeController.
 */
@Controller
@RequestMapping("/welcome")
public class WelcomeController extends BaseController {

	/** The gmail service. */
    @Autowired
    private GMailService gmailService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ynov.dap.web.BaseController#getClassName()
	 */
	@Override
	protected String getClassName() {
		return null;
	}
    @RequestMapping("/{userId}")
    public String welcome(@PathVariable final String userId, final ModelMap model) throws
            IOException, GeneralSecurityException, UserException {
        AppUser user = getUserById(userId);
        Integer nbMail = gmailService.getNbUnreadMailAllAccount(user);
        Integer nbunreadEmails = nbMail;
        model.addAttribute("nbEmails", nbunreadEmails);
        return "welcome";

    }
    

}
