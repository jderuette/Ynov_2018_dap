package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import fr.ynov.dap.data.GoogleAccountData;

/**.
 * This class extends GoogleService and provides calendar service features
 * @author Dom
 *
 */
@Service
public class CalendarService extends GoogleService {

    /**
     * @param userRepository .
     */
    @Autowired
    private AppUserRepository userRepository;

    /**.
     * Return the service of calendar according userId param
     * @param userId . TODO phd by Djer |JavaDoc| Il faut lire "accountName" ici je supose ?
     * @return .
     * @throws GeneralSecurityException .
     * @throws IOException .
     */
    public Calendar getService(final String userId) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(userId))
                .setApplicationName(getConfig().getApplicationName()).build();
        return service;
    }

    /**.
     * Return the next event according the userId param in format Map<String,Object>
     * @param userId .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    @SuppressWarnings("deprecation")
    public Map<String, Object> getNextEventFormatMap(final String userId) throws IOException, GeneralSecurityException {
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = getService(userId).events().list("primary").setMaxResults(1).setTimeMin(now)
                .setOrderBy("startTime").setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        Map<String, Object> mHashMap = new HashMap<>();
        if (items.isEmpty()) {

            mHashMap.put("eventUncomming", "No upcoming events found");

        } else {
            for (Event event : items) {
                Date eventBegin = new Date(event.getStart().getDateTime().getValue());
                Date eventFinish = new Date(event.getEnd().getDateTime().getValue());
                mHashMap.put("eventUncomming", event.getSummary());
                //TODO phd by Djer |IDE| Pourquoi ne pas utiliser l'alternative "non dépréciée" ?
                mHashMap.put("eventBegin", eventBegin.toLocaleString());
                mHashMap.put("eventFinish", eventFinish.toLocaleString());
                mHashMap.put("eventStatus", event.getAttendees());
            }
        }
        return mHashMap;
    }

    /**
     *
     * @param userKey .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    public Event getNextEventForAll(final String userKey) throws IOException, GeneralSecurityException {

        AppUser user = userRepository.findByName(userKey);
        List<GoogleAccountData> accounts = user.getGoogleAccounts();
        List<Event> listEvent = new ArrayList<>();
        Event event = null;
        Event eventMostRecent = null;

        for (GoogleAccountData accountData : accounts) {
            event = getNextEvent(accountData.getAccountName());
            listEvent.add(event);
        }
      //TODO phd by Djer |POO| Nom de variable qui prete à confusion, il s'agit en faite de "now". Relis ton algo qui cherche le plus récent et tu veras qu'il ne fonctionne pas bien
        long dateMillisecondsMostRecent = System.currentTimeMillis();
        long dateMilliseconds;

        if (listEvent != null) {
            for (int i = 0; i < listEvent.size(); i++) {
                //TODO phd by Djer |API Google| Attention getStart().getDateTime() est null pour les évènnement qui dure toutes la journée
                dateMilliseconds = listEvent.get(i).getStart().getDateTime().getValue();
                if (dateMillisecondsMostRecent > dateMilliseconds) {
                    eventMostRecent = listEvent.get(i);
                }
            }
        }

        return eventMostRecent;
    }

    /**
     *
     * @param accountName .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    public Event getNextEvent(final String accountName) throws IOException, GeneralSecurityException {
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = getService(accountName).events().list("primary").setMaxResults(1).setTimeMin(now)
                .setOrderBy("startTime").setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        Event event = null;
        if (items.size() > 0) {
            event = items.get(0);
        }
        return event;
    }

}
