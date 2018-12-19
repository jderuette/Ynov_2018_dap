package fr.ynov.dap.services.google;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.exceptions.ServiceException;

/**
 * Google Calendar Service.
 */
@Service
public final class GCalendarService extends GoogleService {
    /**
     * Logger used for logs.
     */
    //TODO jaaa by Djer |Log4J| Devrait être final, pas besoin de 1 par instance (ici tu as un Singleton donc peu probable, cependant laisser le static final précise ton intention)
    private static Logger log = LogManager.getLogger();

    /**
     * AppUserRepository.
     */
    @Autowired
    private AppUserRepository repository;

    /**
     * @param userKey user key for authentication.
     * @return Calendar.
     * @throws ServiceException exception
     */
    public Calendar getService(final String userKey) throws ServiceException {
        log.info("getCalendarService called with userKey=" + userKey);
        NetHttpTransport httpTransport;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(userKey))
                    .setApplicationName(getConfig().getApplicationName())
                    .build();
            return service;
        } catch (GeneralSecurityException e) {
            //TODO jaa by Djer |Log4J| Lorsque tu log une exception, essaye toujours d'ajouter un message (avec du contexte ("Error while creating Calendar service for userkey : " + userKey)
            log.error(e);
            throw new ServiceException("can't get the Calendar service. Error: " + e.getMessage(), e);
        } catch (IOException e) {
          //TODO jaa by Djer |Log4J| Ajoute un emssage (et du contexte)
            log.error(e);
            throw new ServiceException("can't get the Calendar service. Error: " + e.getMessage(), e);
        } catch (Exception e) {
          //TODO jaa by Djer |Log4J| Ajoute un emssage (et du contexte)
            log.error(e);
            throw new ServiceException("can't get the Calendar service. Error: " + e.getMessage(), e);
        }
    }

    /**
     * Get the next event from now.
     * @param userKey used for authentication.
     * @return near event from now
     * @throws Exception exception
     */
    public Event getNextEvent(final String userKey) throws Exception {
        final Integer maxResults = 1;
        DateTime now = new DateTime(System.currentTimeMillis());
        log.info("getNextEvent called. userKey=" + userKey + "; with maxResults="
                //TODO jaa by Djer |POO| Indiquer "DateNow" n'est pas très utile, "from : " serait plus claire
                + maxResults + "; DateNow=" + now.toString());
        Events events = getService(userKey).events().list("primary")
                .setMaxResults(maxResults)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
      //TODO jaa by Djer |Log4J| Contextualise chacunsde tes messages (" for userKey : " + userKey). Sur un serveur tu peux avoir plusieurs traitement en paralelle, et tu ne sauras pas interpreter ce messages dans les logs
        log.info("found event(s)=" + events.toString());
        if (items.isEmpty()) {
            //TODO jaa by Djer |POO| Evite les multipels return dans une même méthode
            return null;
        }

        return items.get(0);
    }

    /**
     * Get next event for all GoogleAccount of a UserApp.
     * @param userKey userKey.
     * @return next Event.
     * @throws Exception exception.
     */
    public Event getNextEventForAllAccount(final String userKey) throws Exception {
        AppUser appUser = repository.findByUserKey(userKey);
        List<String> names = appUser.getGoogleAccountNames();
        List<Event> events = new ArrayList<Event>();

        for (String name : names) {
            Event event = getNextEvent(name);
            if (event != null) {
                events.add(event);
            }
        }

        if (events.isEmpty()) {
            return null;
        }

        Event nextEvent = events.get(0);

        for (Event event : events) {
            if (nextEvent.getStart().getDateTime().getValue() > event.getStart().getDateTime().getValue()) {
                nextEvent = event;
            }
        }

        return nextEvent;
    }
}
