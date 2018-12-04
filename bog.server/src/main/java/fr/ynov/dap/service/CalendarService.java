package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccountData;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * @author Gaël BOSSER
 * Manage the redirection to the Controller
 * Extends MainService
 *
 */
@Service
public class CalendarService extends GoogleService {

    /**.
     * TODO bog by Djer |Spring| Oui géré par Spring mais PAS dans le loadConfig ! Géré grace à des annotations !
     * repositoryUser is managed by Spring on the loadConfig()
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**.
     * Constructor CalendarService
     * @throws Exception si un problème est survenu lors de la création de l'instance CalendarService
     * @throws IOException si un problème est survenu lors de la création de l'instance CalendarService
     */
    public CalendarService() throws Exception, IOException {
        super();
    }

    /**
     * @param accountName correspondant à l'utilisateur dont il faut créé le service calendar
     * accountName parameter put by client
     * @return le calendar lié à l'userId passé en paramètre
     * @throws IOException si un problème est survenu lors de l'appel à cette fonction
     * @throws GeneralSecurityException si un problème est survenu lors de l'appel à cette fonction
     */
    private Calendar buildCalendarService(final String accountName) throws IOException, GeneralSecurityException {
        Calendar service = new Calendar.Builder(getHttpTransport(), getJsonFactory(), getCredentials(accountName))
                .setApplicationName(getConfig().getApplicationName()).build();
        return service;
    }

    /**
     * @param userKey nom utilisateur
     * @return next event
     * @throws IOException Exception levé si problème survenu lors durant le traitement de la fonction
     * @throws GeneralSecurityException Exception levé si problème survenu lors durant le traitement de la fonction
     */
    public Event getNextEvent(final String userKey) throws IOException, GeneralSecurityException {
        DateTime now = new DateTime(System.currentTimeMillis());
        Event nextEventResult = null;
        AppUser user = repositoryUser.findByName(userKey);
        List<GoogleAccountData> accounts = user.getAccounts();
        Event event = null;
        List<Event> events = new ArrayList<Event>();
        for (GoogleAccountData accountData : accounts) {
            events = buildCalendarService(accountData.getAccountName()).events().list("primary").setMaxResults(1)
                    .setTimeMin(now).setOrderBy("startTime").setSingleEvents(true).execute().getItems();
            if (!events.isEmpty()) {
                event = events.get(0);
                if (nextEventResult != null) {

                    Date eventDate;
                    if (event.getStart().getDate() != null) {
                        eventDate = new Date(event.getStart().getDate().getValue());
                    } else {
                        eventDate = new Date(event.getStart().getDateTime().getValue());
                    }
                    Date nextEventResulteDate;
                    if (nextEventResult.getStart().getDate() != null) {
                        nextEventResulteDate = new Date(nextEventResult.getStart().getDate().getValue());
                    } else {
                        nextEventResulteDate = new Date(nextEventResult.getStart().getDateTime().getValue());
                    }
                    if (eventDate.before(nextEventResulteDate)) {
                        nextEventResult = event;
                    }
                } else {
                    nextEventResult = event;
                }
            }
        }
        return nextEventResult;
    }
}
