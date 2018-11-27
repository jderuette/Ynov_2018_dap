package fr.ynov.dap.microsoft.controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.microsoft.AuthHelper;
import fr.ynov.dap.microsoft.data.*;
import fr.ynov.dap.microsoft.models.TokenResponse;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.microsoft.service.OutlookServiceBuilder;

@Controller
public class ContactsController {
  @RequestMapping("/contacts")
  public String contacts(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    HttpSession session = request.getSession();
    TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
    if (tokens == null) {
      // No tokens in session, user needs to sign in
      redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
      return "redirect:/index.html";
    }

    String tenantId = (String)session.getAttribute("userTenantId");

    tokens = AuthHelper.ensureTokens(tokens, tenantId);

    String email = (String)session.getAttribute("userEmail");

    OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

    // Sort by given name in ascending order (A-Z)
    String sort = "GivenName ASC";
    // Only return the properties we care about
    String properties = "GivenName,Surname,CompanyName,EmailAddresses";
    // Return at most 10 contacts
    Integer maxResults = 10;

    try {
      PagedResult<Contact> contacts = outlookService.getContacts(
          sort, properties, maxResults)
          .execute().body();
      model.addAttribute("contacts", contacts.getValue());
    } catch (IOException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/index.html";
    }

    return "contacts";
  }
}