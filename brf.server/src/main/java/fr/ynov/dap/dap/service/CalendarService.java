package fr.ynov.dap.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccountData;

/**
 * @author Florian BRANCHEREAU
 * Extends MainService
 *
 */
@Service
public class CalendarService extends GoogleService {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();
    /**.
     * Création de l'objet AppUserRepository
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * @throws Exception constructeur
     * @throws IOException constructeur
     */
    public CalendarService() throws Exception, IOException {
    }

    /**
     * @param userKey Nom du compte
     * @return calendarservice
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    private Calendar buildCalendarService(final String userKey) throws IOException, GeneralSecurityException {
        Calendar calendarservice = new Calendar.Builder(getHttpTransport(), getJsonFactory(), getCredentials(userKey))
                .setApplicationName(getConfiguration().getApplicationName())
                .build();
        return calendarservice;
    }

    /**
     * @param userKey nom du compte
     * @return List<Event>
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    public String getNextEvents(final String userKey) throws IOException, GeneralSecurityException {
        String response = null;
        DateTime now = new DateTime(System.currentTimeMillis());
        try {
            List<Event> nextevent = buildCalendarService(userKey).events().list("primary")
                    .setMaxResults(1)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute().getItems();
            for (Event event : nextevent) {
                response = "Sujet du prochain évenement : " + event.getSummary() + " - Date de début : "
                        + event.getStart();
                response += " - Date de fin : " + event.getEnd() + " - Etat : " + event.getStatus();
                
                //TODO brf by Djer |API Google| Gestion de "MON" status sur l'event ?
            }
        } catch (Exception e) {
          //TODO brf by Djer |Log4J| Evite d'ajouter le message de l'exception à ton propre message. Passe l'exception en deuxième paramètre et laisse Log4J afficher le message de la "cause" (et la pile)
            LOG.error("Erreur lors de la création de l'evenement calendar : ", e.getMessage());
        }
        //TOOD brf by Djer |MVC| Renvoie juste les données et laisse l'apellant effectuer le formatage
        return response;
    }

    /**
     * @param userKey nom d'utilisateur
     * @throws IOException fonction
     * @return le premier event
     * @throws GeneralSecurityException fonction
     */
    public String getNextEventORM(final String userKey) throws IOException, GeneralSecurityException {

        String response = null;

        AppUser user = appUserRepository.findByName(userKey);
        //TODO brf by Djer |POO| Vérifie si user n'est pas null (userKey inexistant en BDD)
        List<GoogleAccountData> accounts = user.getAccounts();
        List<Event> listEvent = new ArrayList<>();
        Event event = null;
        Event firstEvent = null;

        for (GoogleAccountData accountData : accounts) {
            event = getFirstEvent(accountData.getAccountName());
            listEvent.add(event);
        }
        long dateMsFirstEvent = System.currentTimeMillis();
        long dateMs;

        if (listEvent != null) {
            for (int i = 0; i < listEvent.size(); i++) {
                //TODO brf by Djer |API Google| Attention .getStart().getDateTime() est null pour les évènnements qui durent toute la journée, pour ceux là il faut utiliser .getStart().getDate()
                dateMs = listEvent.get(i).getStart().getDateTime().getValue();
                if (firstEvent == null) {
                    firstEvent = listEvent.get(i);
                } else if (dateMsFirstEvent > dateMs) {
                    firstEvent = listEvent.get(i);
                }
            }
        }

        response = "Sujet du prochain évenement : " + firstEvent.getSummary() + " - Date de début : "
                + firstEvent.getStart();
        response += " - Date de fin : " + event.getEnd() + " - Etat : " + firstEvent.getStatus();
        //TOOD brf by Djer |MVC| Renvoie juste les données et laisse l'apellant effectuer le formatage
        return response;
    }

    /**.
     * @param accountName nom du compte
     * @return le premier évènement trouvé
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    public Event getFirstEvent(final String accountName) throws IOException, GeneralSecurityException {
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = buildCalendarService(accountName).events().list("primary").setMaxResults(1).setTimeMin(now)
                .setOrderBy("startTime").setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        Event event = null;
        if (items != null) {
            //TODO brf by Djer |POO| La liste peu ne pas etre "null" ET ne contenir aucun élement, tu auras alors un "ArrayoutOfBoundException" lors du .get(0)
            event = items.get(0);
        }
        return event;
    }
}
