package fr.ynov.dap.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.exceptions.ServiceException;
import fr.ynov.dap.services.microsoft.Message;
import fr.ynov.dap.services.microsoft.MicrosoftMailService;

/**
 * Microsoft mail controller.
 */
@Controller
public class MailsController {
    /**
     * Get the Microsoft mail service thanks to the dependency injection.
     */
    @Autowired
    private MicrosoftMailService mailService;

    @RequestMapping("/microsoftMails")
    public String microsoftIndex(@RequestParam("userKey") final String userKey,
            final ModelMap model, final HttpServletRequest request) {

        model.addAttribute("fragment", "fragments/microsoftMailFragment");
        List<List<Message>> messagesFoEachAccount;

        try {
            mailService.setUserKey(userKey);
            messagesFoEachAccount = mailService.getFirstEmails(userKey);
            model.addAttribute("messagesForAccounts", messagesFoEachAccount);
        } catch (ServiceException e) {
            model.addAttribute("error", e);
        }
        return "base";
    }

}
