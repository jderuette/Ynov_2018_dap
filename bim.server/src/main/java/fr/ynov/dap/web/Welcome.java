package fr.ynov.dap.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.DataStore;
import com.google.api.services.gmail.Gmail;

import fr.ynov.dap.google.GmailService;

/**
 * Hello world.
 * @author MBILLEMAZ
 *
 */
@Controller
public class Welcome {

    /**
     * own gmail service.
     */
    @Autowired
    private GmailService gmailService;

    /**
     * return helloWorld template.
     * @param model model
     * @return helloWorld template
     * @throws Exception 
     */
    @RequestMapping("/")
    public String welcome(final ModelMap model) throws Exception {
        Gmail service = gmailService.getService("toto");
        model.addAttribute("nbEmails", service.users().labels().get("toto", "INBOX").execute().getMessagesUnread());
        return "welcome";
    }

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
}
