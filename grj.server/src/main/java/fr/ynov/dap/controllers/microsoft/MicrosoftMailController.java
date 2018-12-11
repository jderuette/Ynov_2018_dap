package fr.ynov.dap.controllers.microsoft;

import fr.ynov.dap.helpers.MicrosoftAuthHelper;
import fr.ynov.dap.models.common.User;
import fr.ynov.dap.models.microsoft.*;
import fr.ynov.dap.repositories.UserRepository;
import fr.ynov.dap.services.microsoft.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * MicrosoftMailController
 */
@Controller
public class MicrosoftMailController {

    /**
     * Autowired UserRepository
     */
    @Autowired
    UserRepository userRepository;

    private static final int MAX_RESULT = 10;

    /**
     * Show account microsoft mail
     *
     * @param model              model
     * @param request            request
     * @param redirectAttributes redirectAttributes
     * @param userName           userName
     * @return String
     */
    @RequestMapping("/microsoft/mail/{userName}")
    public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, @PathVariable final String userName) {

        User user = userRepository.findByName(userName);

        Map<String, MicrosoftMessage[]> mails = new HashMap<>();

        if (user != null) {
            //TODO grj by Djer |SOA| Un petit "Microsoft mail service" serait pas mal.

            List<MicrosoftAccount> microsoftAccountList = user.getMicrosoftAccountList();

            for (MicrosoftAccount currentMicrosoftAccount : microsoftAccountList) {
                MicrosoftAuthHelper.ensureTokens(currentMicrosoftAccount);

                OutlookService outlookService = OutlookServiceBuilder.getOutlookService(currentMicrosoftAccount.getToken(), currentMicrosoftAccount.getEmail());

                // Retrieve messages from the inbox
                String folder = "inbox";
                // Sort by time received in descending order
                String sort = "receivedDateTime DESC";
                // Only return the properties we care about
                String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
                // Return at most 10 messages
                Integer maxResults = MAX_RESULT;

                try {
                    MicrosoftMessage[] messages = outlookService.getMessages(folder, sort, properties, maxResults).execute().body().getValue();
                    mails.put(currentMicrosoftAccount.getName(), messages);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            return "error";
        }

        model.addAttribute("mailsMap", mails);
        model.addAttribute("userName", user.getName());
        return "mails";
    }
}
