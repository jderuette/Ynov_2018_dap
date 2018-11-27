package fr.ynov.dap.services.google;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ContactGroup;

import fr.ynov.dap.data.AppUser;

/**
 * Google people service.
 */
@Service
public class PeopleGoogleService extends GoogleService {
    /**
     * Logger used for logs.
     */
    private static Logger log = LogManager.getLogger();

    /**
     * Get PeopleService.
     * @param userKey user key needed for authentication
     * @return PeopleService
     * @throws Exception exception
     */
    public PeopleService getService(final String userKey) throws Exception {
        log.info("getPeopleService called with userKey=" + userKey);
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        PeopleService service = new PeopleService.Builder(httpTransport, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(getConfig().getApplicationName()).build();

        return service;
    }
    /**
     * get the number of contacts.
     * @param userKey userKey needed for authentication.
     * @return the number of contacts.
     * @throws IOException exception
     * @throws Exception exception
     */
    public Integer getNumberOfContacts(final String userKey) throws IOException, Exception {
        log.info("getNumberOfContacts called with userKey=" + userKey);
        ContactGroup group = getService(userKey).contactGroups().get("contactGroups/all").execute();
        if (group == null || group.getMemberCount() == null) {
            return 0;
        }

        return group.getMemberCount();
    }

    /**
     * Get the number of contacts for all google account of an AppUser account.
     * @param userKey userKey of the AppUser account.
     * @return total number of unread google mail for an AppUser.
     * @throws Exception exception
     */
    public Integer getNumberOfAllContactsOfAllAccount(final String userKey) throws Exception {
        AppUser appUser = getRepository().findByUserKey(userKey);
        List<String> names = appUser.getGoogleAccountNames();
        Integer totalNumberofContacts = 0;

        for (String name : names) {
            totalNumberofContacts += getNumberOfContacts(name);
        }

        return totalNumberofContacts;
    }
}
