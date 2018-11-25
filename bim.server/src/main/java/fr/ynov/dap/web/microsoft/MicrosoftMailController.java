package fr.ynov.dap.web.microsoft;

import java.io.IOException;
import java.util.HashMap;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.microsoft.Message;
import fr.ynov.dap.data.microsoft.PagedResult;
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
     * User repository.
     */
    @Autowired
    private AppUserRepository userRepository;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * get last 10 outlook mails for each account.
     * @param model model
     * @param userKey user
     * @return next mails
     **/
    @RequestMapping("/email/microsoft/nextMessages")
    public String getNextMails(ModelMap model, @RequestParam("userKey") final String userKey) {
        AppUser user = userRepository.findByName(userKey);
        try {
            HashMap<String, PagedResult<Message>> messages = outlookService.getNextMessages(user);
            model.addAttribute("listMessages", messages);
        } catch (IOException e) {
            return "redirect:/index";
        }

        model.addAttribute("current", "mail");
        model.addAttribute("fragFile", "mail");
        model.addAttribute("frag", "mailBody");
        return "base";
    }

    /**
     * get number of unread mails.
     * @param userKey user
     * @return nb unread mail
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