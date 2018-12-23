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
import fr.ynov.dap.dap.microsoft.service.Event;
import fr.ynov.dap.dap.microsoft.service.EventService;

/**
 * @author Florian
 */
@Controller
public class EventsController {

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
     * @return La page event
     */
    //TODO brf by Djer |Spring| Si tu n'as pas besoin de "request" ne le met pas dans la signature de ta méthode
    @RequestMapping("/events")
    public String events(@RequestParam("userKey") final String userKey, Model model, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        AppUser appUser = appUserRepository.findByName(userKey);
        Event[] events = null;
        if (appUser != null) {
            model.addAttribute("accounts", appUser.getMicrosoftAccounts());
            for (MicrosoftAccountData account : appUser.getMicrosoftAccounts()) {
                //TODO brf by Djer |POO| To algo est faux, tu revera toujour le "first Event" du dernier Mircrosoft Account. Il FAUT que tu compare les dates. Même si tu passe les précédent "events" comme dans le service tu ne compare les dates le PB reste identique
                events = EventService.firstEvent(redirectAttributes, account, model, userKey, events);
            }
            //TODO brf by Djer |Log4J| Contextualise tes messages (" for userKey : ")
            LOG.debug("Affichage de l'evenement : " + events);
            model.addAttribute("events", events);
            model.addAttribute("logoutUrl", "/logout");
            model.addAttribute("mail", "/mail?userKey=" + userKey);
            model.addAttribute("contact", "/contacts?userKey=" + userKey);
            return "event";
        }
        //TODO brf by Djer |Log4J| (else) Une petite log ?
        return "redirect:/";
    }
}
