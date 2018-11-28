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
import fr.ynov.dap.dap.microsoft.service.MailService;

/**
 * @author Florian
 */
@Controller
public class MailController {

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
     * @return la page mail
     */
    @RequestMapping("/mail")
    public String mail(@RequestParam("userKey") final String userKey, final Model model,
            final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {
        AppUser appUser = appUserRepository.findByName(userKey);
        String MailNonLu = "";
        int unreadMails = 0;
        if (appUser != null) {
            model.addAttribute("accounts", appUser.getMicrosoftAccounts());
            for (MicrosoftAccountData account : appUser.getMicrosoftAccounts()) {
                MailNonLu = MailService.NombreDeMail(redirectAttributes, account, model, userKey);
                LOG.debug("Nombre de mail non lu : " + MailNonLu + " ,Compte total : " + unreadMails);
                unreadMails += new Integer(MailNonLu).intValue();
            }
            model.addAttribute("logoutUrl", "/logout");
            model.addAttribute("evenement", "/events?userKey=" + userKey);
            model.addAttribute("contact", "/contacts?userKey=" + userKey);
            model.addAttribute("unreadMails", unreadMails);
            return "mail";
        }
        return "redirect:/";
    }
}
