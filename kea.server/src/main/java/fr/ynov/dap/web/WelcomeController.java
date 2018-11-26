package fr.ynov.dap.web;

import fr.ynov.dap.GmailService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller is used for the welcomePage before being connected.
 * @author Antoine
 *
 */
@Controller
public class WelcomeController {

  /**
   * the gmail service from fr.ynov.dap.
   */
  @Autowired
  private GmailService gmail;

  /**
   * Shows to the user his unread mails in a view.
   * @param userKey the identifier used by the application
   * @param model the object that can be used in view
   * @return the fileName of the view
   * @throws IOException nothing special
   */
  @RequestMapping("/nbmails/{user}")
  public String welcome(@PathVariable final String userKey, final ModelMap model)
      throws IOException {
    model.addAttribute("nbEmails", gmail.getUnreadMessageCount(userKey));
    return "welcome";
  }
}
