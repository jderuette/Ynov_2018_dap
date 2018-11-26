package fr.ynov.dap.web.microsoft;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.microsoft.authentication.AuthHelper;
import fr.ynov.dap.microsoft.authentication.TokenResponse;
import fr.ynov.dap.web.HandlerErrorController;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.microsoft.Message;
import fr.ynov.dap.microsoft.MicrosoftMailService;
import fr.ynov.dap.microsoft.OutlookService;
import fr.ynov.dap.microsoft.OutlookServiceBuilder;
import fr.ynov.dap.microsoft.PagedResult;

@Controller
public class MicrosoftMailController extends HandlerErrorController {

    @Autowired
    private MicrosoftMailService microsoftMailService;

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository repositoryUser;

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
