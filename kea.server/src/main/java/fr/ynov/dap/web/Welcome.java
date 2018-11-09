package fr.ynov.dap.web;

import fr.ynov.dap.GmailService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Welcome {

  @Autowired
  GmailService gmail;

  @RequestMapping("/nbmails/{user}")
  public String welcome(@PathVariable String userKey, ModelMap model)
      throws IOException {
    model.addAttribute("nbEmails", gmail.getUnreadMessageCount(userKey));
    return "welcome";
  }
}
