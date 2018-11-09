package fr.ynov.dap.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.google.GmailService;
import fr.ynov.dap.microsoft.OutlookService;

/**
 * root controller.
 * @author MBILLEMAZ
 *
 */
@Controller
public class MicrosoftIndexController {

    /**
     * own gmail service.
     */
    @Autowired
    private GmailService gmailService;

    @Autowired
    private OutlookService outlookService;

    @Autowired
    private AppUserRepository repository;

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * get users.
     * @param model model
     * @return users
     * @throws IOException exception
     */
    @RequestMapping("/dataStore")
    public String getDataStore(final ModelMap model) throws IOException {
        GoogleAuthorizationCodeFlow flow = gmailService.getFlow();
        DataStore<StoredCredential> cred = flow.getCredentialDataStore();

        Map<String, StoredCredential> map = new HashMap<String, StoredCredential>();

        List<String> keys = new ArrayList<String>(cred.keySet());
        List<StoredCredential> values = new ArrayList<StoredCredential>(cred.values());

        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }

        model.addAttribute("list", map);
        return "dataStore";
    }

    @RequestMapping("/email/all/nbUnread")
    @ResponseBody
    public Integer getNbUnreadmails(@RequestParam("userKey") final String userKey) {
        AppUser user = repository.findByName(userKey);

        Integer nbUnread = -1;
        try {
            nbUnread = outlookService.getNbUnread(user);
            nbUnread += gmailService.getNbUnreadMailForUser(user, "me");
        } catch (IOException e) {
            LOGGER.error("Impossible de récupérer les mails outlook de l'utilisateur " + userKey, e);
        } catch (Exception e) {
            LOGGER.error("Impossible de récupérer les mails gmail de l'utilisateur " + userKey, e);
        }

        return nbUnread;
    }
}
