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

@Controller
public class MicrosoftContactController {

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
     * get number of microsoft contact in all accounts.
     * @param userKey userkey
     * @return nb unread mail
     */
    @RequestMapping("/contact/microsoft/number")
    @ResponseBody
    public Integer getNbUnreadmails(@RequestParam("userKey") final String userKey) {
        AppUser user = userRepository.findByName(userKey);

        Integer nbContact = -1;
        try {
            nbContact = outlookService.getNbContact(user);
        } catch (IOException e) {
            LOGGER.error("Impossible de récupérer les contacts de l'utilisateur " + userKey, e);
        }

        return nbContact;
    }
}
