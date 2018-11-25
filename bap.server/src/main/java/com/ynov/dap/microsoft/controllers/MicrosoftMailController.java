/*

package com.ynov.dap.microsoft.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynov.dap.data.AppUser;
import com.ynov.dap.microsoft.business.OutlookService;
import com.ynov.dap.microsoft.business.OutlookServiceBuilder;
import com.ynov.dap.microsoft.data.MicrosoftAccount;
import com.ynov.dap.microsoft.models.Message;
import com.ynov.dap.microsoft.models.PagedResult;
import com.ynov.dap.microsoft.models.TokenResponse;
import com.ynov.dap.repositories.AppUserRepository;


@Controller
public class MicrosoftMailController {
	
    @Autowired
    private AppUserRepository appUserRepository;
    
    private Integer getNbUnreadEmails(final MicrosoftAccount microsoftAccount) {

        if (microsoftAccount == null) {
            return 0;
        }

        String email = microsoftAccount.getEmail();
        String tenantId = microsoftAccount.getTenantId();
        TokenResponse tokens = microsoftAccount.getTokenResponse();


        if (StrUtils.isNullOrEmpty(tenantId) || StrUtils.isNullOrEmpty(email) || tokens == null) {
            return 0;
        }
       

        //TokenResponse newTokens = getToken(microsoftAccount);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        // Retrieve messages from the inbox
        String folder = "inbox";
        // Sort by time received in descending order
        String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "IsRead";
        // Return at most 10 messages
        Integer maxResults = 99999;

        try {
          PagedResult<Message> messages = outlookService.getMessages(
              folder, sort, properties, maxResults)
              .execute().body();
          
          return messages.getValue().length;
          
        } catch (IOException e) {
          //redirectAttributes.addFlashAttribute("error", e.getMessage());
          return 0;
        }
    }
*/

 //@RequestMapping("/mail/{gUser}")
 //public String mail(@PathVariable final String gUser, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    //HttpSession session = request.getSession();
    //TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");
    //if (tokens == null) {
      //// No tokens in session, user needs to sign in
      //redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
      //return "redirect:/index";
    //}

    //String tenantId = (String) session.getAttribute("userTenantId");

    //tokens = AuthHelper.ensureTokens(tokens, tenantId);
    
    
      /*
    AppUser appUser = appUserRepository.findByName(gUser);

    Integer numberOfUnreadMails = 0;

    for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {

        numberOfUnreadMails += getNbUnreadEmails(account);

    }
    */

    /*
    String email = (String) session.getAttribute("userEmail");

    OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

    // Retrieve messages from the inbox
    String folder = "inbox";
    // Sort by time received in descending order
    String sort = "receivedDateTime DESC";
    // Only return the properties we care about
    String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
    // Return at most 10 messages
    Integer maxResults = 10;

    try {
      PagedResult<Message> messages = outlookService.getMessages(
          folder, sort, properties, maxResults)
          .execute().body();
      model.addAttribute("messages", messages.getValue());
      System.out.println("model");
      System.out.println(model);
    } catch (IOException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/index";
    }*/
    
    /*
    System.out.println("unread");
    System.out.println(numberOfUnreadMails);
    model.addAttribute("messages", numberOfUnreadMails);

    return "mail";
  }
}
    */