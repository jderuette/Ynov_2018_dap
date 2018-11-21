package fr.ynov.dap.web;

import fr.ynov.dap.GmailService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
  @Autowired
  GmailService gmailService;

  /**
     * returns a string with the amount of unread mails.
     * @param userKey the current user connected
     * @return a string that contains french words and the unread mails
     * @throws IOException nothing special
     */
  @RequestMapping("/email/nbUnread/{userKey}")
  @ResponseBody
  public String unreadMessageCountToString(@PathVariable final String userKey)
      throws IOException {
    gmailService.getLogger().info("get les mails pour le user :" + userKey);
    return "Vous avez " + gmailService.getUnreadMessageCount(userKey)
        + " mails non lus !\n";
  }

  /**
   * Browse all labels and concatenate them in one string to display.
   * @param userKey the userId
   * @return a string with all labels
   * @throws IOException nothing special
   */
  @RequestMapping("/labels/{userKey}")
  @ResponseBody
  public String listeLabelToString(@PathVariable String userKey)
      throws IOException {
    List<String> listeLabels = gmailService.getLabels(userKey);
    String labelsInString = "";
    if (!listeLabels.isEmpty()) {
      for (String label : listeLabels) {
        labelsInString = labelsInString + label + "\n";
      }
    } else {
      labelsInString = "Aucuns labels trouvés !";
    }
    return labelsInString;
  }

}
