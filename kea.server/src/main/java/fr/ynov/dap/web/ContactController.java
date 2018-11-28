package fr.ynov.dap.web;

import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.service.GoogleContactService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.services.people.v1.model.Person;

/**
 * this controller manages the People API to displau contacts and profile info.
 * @author Antoine
 *
 */
@Controller
public class ContactController {
  /**
   * the gmailService to get mails information.
   */
  @Autowired
  private GoogleContactService contactsService;
  /**
   * the appUserRepository manages all database accesses for The AppUser.
   */
  @Autowired
  private AppUserRepository appUserRepo;

  /**
   * returns a string with the amount of all unread mails.
   * @param accountName the current user connected
   * @param model the model given to the template
   * @return a string that contains french words and all the unread mails
   * @throws IOException nothing special
   */
  @RequestMapping("/contacts/show/{accountName}")
  public String allContactsForAppUser(@PathVariable final String accountName,
      final Model model) throws IOException {
    contactsService.getLogger()
        .info("get les contacts pour le user :" + accountName);
    List<Person> listeContacts = new ArrayList<Person>();
    List<GoogleAccount> userGoogleAccounts = appUserRepo
        .findByUserKey(accountName).getGoogleAccounts();
    //Person profile = contactsService.getprofile(userGoogleAccounts.get(0).getGoogleAccountName());
    for (int i = 0; i < userGoogleAccounts.size(); i++) {
      if (contactsService.getContacts(
          userGoogleAccounts.get(i).getGoogleAccountName()) != null) {
        listeContacts.addAll(contactsService
            .getContacts(userGoogleAccounts.get(i).getGoogleAccountName()));
      }
    }
    model.addAttribute("contacts", listeContacts);
    model.addAttribute("nbContacts", listeContacts.size());
    model.addAttribute("username", accountName);
    model.addAttribute("nbAccount", userGoogleAccounts.size());
    //model.addAttribute("emails", profile.getEmailAddresses());
    //TODO parcourir les contacts microsoft
    return "contacts";
  }
}
