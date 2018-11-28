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

@RestController
@RequestMapping("/welcome")
public class WelcomeController extends BaseController {

    /**
     * Gmail service instance.
     */
    @Autowired
    private GMailService gmailService;

	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * Default method.
     * @param userId User's Id
     * @param model Model
     * @return View
     * @throws GeneralSecurityException Exception
     * @throws IOException Exception
     * @throws NoConfigurationException Exception
     * @throws UserNotFoundException Exception
     * @throws NoGoogleAccountException Exception
    */
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
