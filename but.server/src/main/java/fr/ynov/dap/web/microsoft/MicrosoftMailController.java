package fr.ynov.dap.web.microsoft;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.web.HandlerErrorController;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.microsoft.MicrosoftMailService;

/**
 * Controller to manage microsoft mail.
 * @author thibault
 *
 */
@Controller
public class MicrosoftMailController extends HandlerErrorController {

    /**
     * Microsoft mail service.
     */
    @Autowired
    private MicrosoftMailService microsoftMailService;

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**
     * Route to show my microsoft mails.
     * @param model the data model (transfer to view)
     * @param userKey the user app key
     * @return the template name
     * @throws IOException if bad request.
     */
    @RequestMapping("/microsoft/mail")
    public String mail(final Model model, @RequestParam("userKey") final String userKey)
            throws IOException {

        AppUser user = repositoryUser.findByUserKey(userKey);
        if (user == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' not found.");
        }

        model.addAttribute("emailAccounts", microsoftMailService.getEmails(user));
        model.addAttribute("fragment", "microsoft/mail");
        return "base";
    }
}
