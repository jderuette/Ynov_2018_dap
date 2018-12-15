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
import fr.ynov.dap.comparator.Sorter;
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
	private final Logger LOG = LogManager.getLogger(GoogleAccountService.class);
	
	/**
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	@Override
    protected final String getClassName() {
        return CalendarService.class.getName();
	}
	
	/**
	 * Gets the google client.
	 *
	 * @param credential the credential
	 * @param httpTransport the http transport
	 * @param appName the app name
	 * @return the google client
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

        //TODO cha by Djer |IDE| Ton IDE te dit que ca n'est pas utilisé. Bug ? A supprimer ? 
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
            return null;
        }

        Collections.sort(events, new Sorter());

        return events.get(0);

    }
}
