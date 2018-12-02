package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.oauth2.model.Userinfoplus;

import fr.ynov.dap.Constant;
import fr.ynov.dap.comparateur.Sorter;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.model.GoogleCalendarEvent;

/**
 * The Class CalendarService.
 */
@Service
public class CalendarService extends GoogleService<Calendar> {
	
	/** The o auth service. */
	@Autowired
	private OAuthService oAuthService;
	
	/** The log. */
	//TODO bal by Djer |IDE| Ton IDE t'indique que cette constante n'est pas utilisée. Utilise là ou sppirme la.
	private final Logger LOG = LogManager.getLogger(GoogleAccountService.class);
	
	/* (non-Javadoc)
	 * @see fr.ynov.dap.google.GoogleService#getClassName()
	 */
	@Override
    protected final String getClassName() {
        return CalendarService.class.getName();
	}
	
	/* (non-Javadoc)
	 * @see fr.ynov.dap.google.GoogleService#getGoogleClient(com.google.api.client.auth.oauth2.Credential, com.google.api.client.http.javanet.NetHttpTransport, java.lang.String)
	 */
	@Override
    protected final Calendar getGoogleClient(final Credential credential, final NetHttpTransport httpTransport, 
            final String appName) {
        return new Calendar.Builder(httpTransport, getJSON_FACTORY(), credential).setApplicationName(appName).build();
	}

    /**
     * Get next user's event.
     *
     * @param accountName Current user id
     * @param userEmail Current user mail
     * @return Next event of user linked by the params userId
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     */
    private GoogleCalendarEvent getNextEvent(final String accountName, final String userEmail)
            throws GeneralSecurityException, IOException {

        Calendar calendarService = getService(accountName);

      //TODO bal by Djer |IDE| Ton IDE t'indique que cette constante n'est pas utilisée. Bug ? Remplacé 3 lignes plus bas ?
        DateTime now = new DateTime(System.currentTimeMillis());

        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        long epochMillis = utc.toEpochSecond() * Constant.SECOND_TO_MILLISECOND;

        Events events = calendarService.events().list("primary").setMaxResults(1).setTimeMin(new DateTime(epochMillis))
                .setOrderBy("startTime").setSingleEvents(true).execute();

        List<Event> items = events.getItems();

        if (items.size() > 0) {
            Event gEvent = items.get(0);
            GoogleCalendarEvent evnt = new GoogleCalendarEvent(gEvent, userEmail);
            return evnt;
        }

        //TODO bal by Djer |POO| Evite les multiples returns dans une même méthode
        return null;

    }

    /**
     * Get next user's event.
     * @param user DaP User
     * @return User's next event
     * @throws GeneralSecurityException Security exception
     * @throws IOException Exception
     */
    public GoogleCalendarEvent getNextEvent(final AppUser user) throws GeneralSecurityException, IOException {

        if (user.getGoogleAccounts().size() == 0) {
            return null;
        }

        ArrayList<GoogleCalendarEvent> events = new ArrayList<>();

        for (GoogleAccount googleAccount : user.getGoogleAccounts()) {

            String accountName = googleAccount.getAccountName();
            Userinfoplus userInfo = oAuthService.getUserInfo(accountName);
            GoogleCalendarEvent evnt = getNextEvent(accountName, userInfo.getEmail());

            if (evnt != null) {
                events.add(evnt);
            }
        }

        if (events.size() == 0) {
          //TODO bal by Djer |POO| Evite les multiples returns dans une même méthode
            return null;
        }

        Collections.sort(events, new Sorter());
        return events.get(0);

    }
}
