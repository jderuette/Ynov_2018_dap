package fr.ynov.dap.services.microsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.exceptions.ServiceException;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * Microsoft Calendar Service.
 */
@Service
public class MicrosoftCalendarService extends MicrosoftService {

    /**
     * Get Events of a Microsoft Account.
     * @param account Microsoft account.
     * @return ResultPages that contains the events (Event) of the specified Microsoft Account.
     * @throws ServiceException exception of this service.
     */
    public Event getNextEvent(final MicrosoftAccount account) throws ServiceException {
        getLog().info("Try to get microsoft events with accountName=" + account.getAccountName());

        TokenResponse tokens = getTokens(account);
        String email = getEmail(account);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        String sortByStartTimeInDescendingOrder = "start/dateTime DESC";
        String properties = "organizer,subject,start,end,isOrganizer";
        Integer maxResults = MAX_RESULTS;

        try {
          PagedResult<Event> events = outlookService.getEvents(
              sortByStartTimeInDescendingOrder, properties, maxResults)
              .execute().body();
          if (events.getValue() == null) {
              return null;
          }
          return events.getValue()[0];
        } catch (IOException ioe) {
            getLog().error("An occured while trying to get microsoft events from the API.", ioe);
            throw new ServiceException("Get Microsoft events failed.", ioe);
        }
    }

    /**
     * Get the next event of all Microsoft account.
     * @param userKey user key.
     * @return the next event.
     * @throws ServiceException exception.
     */
    public Event getNextEventsOfAllAccount(final String userKey) throws ServiceException {
        AppUser appUser = getRepository().findByUserKey(userKey);
        List<MicrosoftAccount> accounts = appUser.getMicrosoftAccounts();
        List<Event> events = new ArrayList<Event>();

        for (MicrosoftAccount account : accounts) {
            Event event = getNextEvent(account);
            if (event != null) {
                events.add(event);
            }
        }

        if (events.isEmpty()) {
            return null;
        }

        Event nextEvent = events.get(0);
        for (Event event : events) {
            if (event.getStart().getDateTime().getTime() < nextEvent.getStart().getDateTime().getTime()) {
                nextEvent = event;
            }
        }

        return nextEvent;
    }

}
