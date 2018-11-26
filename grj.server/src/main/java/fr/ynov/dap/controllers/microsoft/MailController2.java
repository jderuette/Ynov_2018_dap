package fr.ynov.dap.controllers.microsoft;

import fr.ynov.dap.helpers.MicrosoftAuthHelper;
import fr.ynov.dap.models.*;
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
import java.util.List;

@Controller
public class MailController2 {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/microsoft/mail/{userName}")
    public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, @PathVariable final String userName) {

        User user = userRepository.findByName(userName);

        if (user != null) {

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
                Integer maxResults = 10;

                try {
                    PagedResult<Message> messages = outlookService.getMessages(folder, sort, properties, maxResults).execute().body();

                    return "ss";
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("error", e.getMessage());
                    return "redirect:/index.html";
                }
            }

        }

        return "dd";
    }
}
