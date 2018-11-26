package fr.ynov.dap.services.microsoft;

import java.io.IOException;

import org.springframework.stereotype.Service;

import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.exceptions.ServiceException;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * Microsoft Calendar Service.
 */
@Service
public class MicrosoftCalendarService extends MicrosoftService {

    /**
     * Constructor.
     * @param key You have to provide the userKey of the AppUser to use.
     */
    public MicrosoftCalendarService(final String key) {
        super(key);
    }

    /**
     * Get Events of a Microsoft Account.
     * @param account Microsoft account.
     * @return ResultPages that contains the events (Event) of the specified Microsoft Account.
     * @throws ServiceException exception of this service.
     */
    public PagedResult<Event> getEvents(final MicrosoftAccount account) throws ServiceException {
        getLog().info("Try to get microsoft events with accountName=" + account.getAccountName());

        TokenResponse tokens = getTokens(account);
        String email = getEmail(account);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        String sortByStartTimeInDescendingOrder = "start/dateTime DESC";
        String properties = "organizer,subject,start,end,isOrganizer";
        Integer maxResults = 10;

        try {
          PagedResult<Event> events = outlookService.getEvents(
              sortByStartTimeInDescendingOrder, properties, maxResults)
              .execute().body();
          return events;
        } catch (IOException ioe) {
            getLog().error("An occured while trying to get microsoft events from the API.", ioe);
            throw new ServiceException("Get Microsoft events failed.", ioe);
        }
    }

}
