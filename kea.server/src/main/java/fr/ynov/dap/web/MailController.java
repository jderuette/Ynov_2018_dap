package fr.ynov.dap.web;

import fr.ynov.dap.GmailService;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.repository.AppUserRepository;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * this controller displays the amount of unread mails the user have.
 * @author Antoine
 *
 */
@Controller
public class MailController {
  /**
   * the gmailService to get mails information.
   */
  @Autowired
  private GmailService gmailService;
  /**
   * the appUserRepository manages all database accesses for The AppUser.
   */
  @Autowired
  private AppUserRepository appUserRepo;

  /**
     * returns a string with the amount of unread mails.
     * @param userKey the google user stored in credentials
     * @return a string that contains french words and the unread mails
     * @throws IOException nothing special
     */
  @RequestMapping("/email/nbUnreadOnlyOne/{userKey}")
  @ResponseBody
  public String unreadMessageCountToString(@PathVariable final String userKey)
      throws IOException {
    gmailService.getLogger().info("get les mails pour le user :" + userKey);
    return "Vous avez " + gmailService.getUnreadMessageCount(userKey)
        + " mails non lus !\n";
  }

  /**
   * returns a string with the amount of all unread mails.
   * @param accountName the current user connected
   * @param model the model given to the template
   * @return a string that contains french words and all the unread mails
   * @throws IOException nothing special
   */
  @RequestMapping("/email/nbUnread/{accountName}")
  public String allUnreadMessageCountToString(
      @PathVariable final String accountName, final Model model) throws IOException {
    gmailService.getLogger().info("get les mails pour le user :" + accountName);
    Integer allNbUnreadMails = 0;
    List<GoogleAccount> userGoogleAccounts = appUserRepo
        .findByUserKey(accountName).getGoogleAccounts();
    for (int i = 0; i < userGoogleAccounts.size(); i++) {
      allNbUnreadMails += gmailService.getUnreadMessageCount(
          userGoogleAccounts.get(i).getGoogleAccountName());
    }
    model.addAttribute("nbUnread", allNbUnreadMails);
    model.addAttribute("username", accountName);
    model.addAttribute("nbAccount", userGoogleAccounts.size());
    //TODO parcourir les mails microsoft
    return "mails";
  }

  /**
   * Browse all labels and concatenate them in one string to display.
   * @param userKey the userId
   * @return a string with all labels
   * @throws IOException nothing special
   */
  @RequestMapping("/labels/{userKey}")
  @ResponseBody
  public String listeLabelToString(final @PathVariable String userKey)
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

  /**
   * get the gmailService.
   * @return the gmailService
   */
  public GmailService getGmailService() {
    return gmailService;
  }

}
