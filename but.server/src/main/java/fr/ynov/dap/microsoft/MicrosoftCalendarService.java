package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.MicrosoftAccountRepository;
import fr.ynov.dap.microsoft.authentication.AuthHelper;
import fr.ynov.dap.web.EventResponse;
import retrofit2.Response;

/**
 * Service to manage calendar microsoft.
 * @author thibault
 *
 */
@Service
public class MicrosoftCalendarService {

    /**
     * Repository of MicrosoftAccount.
     */
    @Autowired
    private MicrosoftAccountRepository repositoryMicrosoftAccount;

    /**
     * Logger for the class.
     */
  //TODO but by Djer |Log4J| Devrait être final
    private static Logger logger = LogManager.getLogger();

    /**
     * Get the next event of one User (all accounts).
     * @param user the app user.
     * @return the EventResponse.
     * @throws IOException if http exception.
     */
    public EventResponse getNextEvent(final AppUser user) throws IOException {
        logger.info("Get next event Microsoft for user : '" + user.getUserKey() + "'");
        Event event = null;
        Boolean organizer = false;
        for (MicrosoftAccount mAccount : user.getMicrosoftAccounts()) {

            mAccount = AuthHelper.ensureTokens(mAccount);
            repositoryMicrosoftAccount.save(mAccount);

            OutlookService outlookService = OutlookServiceBuilder.getOutlookService(mAccount.getAccessToken(),
                    mAccount.getEmail());

            // Sort by start time in descending order
            String sort = "start/dateTime DESC";
            // Only return the properties we care about
            String properties = "organizer,subject,start,end";
            // Only return futur event
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String filter = "start/dateTime ge '" + dateFormat.format(now) + "'";
            // Return at most 1 events
            Integer maxResults = 1;

            Response<PagedResult<Event>> response = outlookService.getEvents(sort, properties, maxResults, filter)
                    .execute();

            if (response.code() >= HttpStatus.SC_BAD_REQUEST) {
                //TODO but by Djer |Log4J| Une petite LOG ? (en Warning à priori)
                throw new HttpResponseException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Error with Microsoft API.");
            }

            Event[] events = response.body().getValue();

            if (events.length > 0) {
                //TODO but by Djer |POO| Attention tu as essayé de mélanger 2 choses "initialisation au premier passage" et "comparaison". S'il y a déja un "event" tu doit comparer avec cette event et pas avec events[0].getStart()
                if (event == null
                        || events[0].getStart().getDateTime().getTime() < event.getStart().getDateTime().getTime()) {
                    event = events[0];
                    organizer = event.getOrganizer().getEmailAddress().getAddress().equals(mAccount.getEmail());
                }
            }
        }

        EventResponse result = null;

        if (event != null) {
            result = new EventResponse();
            result.setStart(event.getStart().getDateTime());
            result.setEnd(event.getEnd().getDateTime());
            result.setSubject(event.getSubject());
            result.setOrganizer(organizer);
        }
        return result;
    }
}
