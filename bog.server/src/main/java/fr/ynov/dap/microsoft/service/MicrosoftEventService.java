package fr.ynov.dap.microsoft.service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.microsoft.data.Event;
import fr.ynov.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.microsoft.data.PagedResult;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * @author Mon_PC
 */
@Service
public class MicrosoftEventService {

    /**.
     * repositoryUser is managed by Spring on the loadConfig()
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**.
     * propriété maxResult
     */
    private static final Integer MAX_RESULT = 1;

    /**
     * @param accessToken token
     * @param email mail user
     * @return events
     * @throws IOException si problème lors de l'éxécution de la fonction
     */
    public PagedResult<Event> getEvents(final String accessToken, final String email) throws IOException {
        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(accessToken, email);

        // Sort by start time in descending order
        String sort = "start/dateTime ASC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";

        String filterDate = "start/dateTime ge ";
        StringBuilder filter = new StringBuilder(filterDate);
        filter.append("'");
        filter.append(Instant.now());
        filter.append("'");

        PagedResult<Event> events = outlookService.getEvents(sort, properties, MAX_RESULT, filter.toString()).execute()
                .body();

        return events;
    }

    /**
     * @param userKey correspondant à l'userKey passé
     * @return nb unread mail for all accounts microsoft
     * @throws IOException problème lors de l'éxécution de la fonction
     */
    public Event getNextEventForAllAccountMicrosoft(final String userKey) throws IOException {

        AppUser user = repositoryUser.findByName(userKey);
        List<MicrosoftAccountData> accounts = user.getAccountsMicrosoft();

        Event nextEvent = null;
        Event event = null;
        for (MicrosoftAccountData account : accounts) {
            event = getEvents(account.getToken().getAccessToken(), account.getUserEmail()).getValue()[0];
            if (nextEvent == null) {
                nextEvent = event;
            } else if (nextEvent.getStart().getDateTime().after(event.getStart().getDateTime())) {
                nextEvent = event;
            }
        }
        return nextEvent;
    }
}
