package fr.ynov.dap.dap.microsoft.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.dap.microsoft.service.ContactService;

/**
 * @author Florian
 */
@Controller
public class ContactsController {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();
    /**
     * Déclaration de appUserRepository
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * @param model .
     * @param request .
     * @param userKey .
     * @param redirectAttributes .
     * @return La page contacts
     */
    @RequestMapping("/contacts")
    public String contacts(@RequestParam("userKey") final String userKey, Model model, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        AppUser appUser = appUserRepository.findByName(userKey);
        String contact = "";
        int nbContact = 0;
        if (appUser != null) {
            model.addAttribute("accounts", appUser.getMicrosoftAccounts());
            for (MicrosoftAccountData account : appUser.getMicrosoftAccounts()) {
                contact = ContactService.nombreDeContact(redirectAttributes, account, model, userKey);
                LOG.debug("Affichage du nombre de contact : " + contact + " , Nombrze total de contact pour le user "
                        + userKey + " : " + nbContact);
                nbContact += new Integer(contact).intValue();
            }
            model.addAttribute("logoutUrl", "/logout");
            model.addAttribute("evenement", "/events?userKey=" + userKey);
            model.addAttribute("mail", "/mail?userKey=" + userKey);
            model.addAttribute("nbContacts", nbContact);
            return "contact";
        }
        return "redirect:/";
    }
}
