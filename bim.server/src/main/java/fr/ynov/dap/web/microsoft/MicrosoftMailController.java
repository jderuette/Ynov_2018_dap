package fr.ynov.dap.web.microsoft;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.microsoft.OutlookService;

/**
 * Mail controller.
 * @author MBILLEMAZ
 *
 */
@Controller
public class MicrosoftMailController {

    /**
     * outlook Service.
     */
    @Autowired
    private OutlookService outlookService;

    /**
     * User repository
     */
    @Autowired
    private AppUserRepository userRepository;

    private static final Logger LOGGER = LogManager.getLogger();

    /*    *//**
            * get last 10 outlook mails.
            * @param model
            * @param request
            * @param redirectAttributes
            * @return 
            *//*
                    @RequestMapping("/email/microsoft/")
                    public String getNextMails(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
                     HttpSession session = request.getSession();
                     TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");
                     if (tokens == null) {
                         // No tokens in session, user needs to sign in
                         redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
                         return "redirect:/index";
                     }
                    
                     try {
                         PagedResult<Message> messages = outlookService.getNextMessages(session, tokens);
                         model.addAttribute("messages", messages.getValue());
                     } catch (IOException e) {
                         redirectAttributes.addFlashAttribute("error", e.getMessage());
                         return "redirect:/index";
                     }
                    
                     model.addAttribute("current", "mail");
                     model.addAttribute("fragFile", "mail");
                     model.addAttribute("frag", "mailBody");
                     return "base";
                    }*/

    /**
     * get number of unread mails.
     * @return
     */
    @RequestMapping("/email/microsoft/nbUnread")
    @ResponseBody
    public Integer getNbUnreadmails(@RequestParam("userKey") final String userKey) {
        AppUser user = userRepository.findByName(userKey);

        Integer nbUnread = -1;
        try {
            nbUnread = outlookService.getNbUnread(user);
        } catch (IOException e) {
            LOGGER.error("Impossible de récupérer les mails outlook de l'utilisateur " + userKey, e);
        }

        return nbUnread;
    }

}