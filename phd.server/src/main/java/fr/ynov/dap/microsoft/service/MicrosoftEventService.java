package fr.ynov.dap.microsoft.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.microsoft.data.Event;
import fr.ynov.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.microsoft.data.PagedResult;

/**
 *
 * @author Dom
 *
 */
@Service
public class MicrosoftEventService {

    /**
    *
    */
    @Autowired
    private AppUserRepository userRepository;

    /**
     *  .
     */
    private static final int MAX_RESULT = 1;

    /**
    *
    * @param accessToken .
    * @param email .
    * @return .
    * @throws IOException .
    */
    public Event[] getNextEvent(final String accessToken, final String email) throws IOException {

        // Sort by start time in ascending order
        String sort = "start/dateTime ASC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";

        String filterDate = "start/dateTime ge ";
        StringBuilder stringBuilderFilter = new StringBuilder();
        stringBuilderFilter.append(filterDate);
        String dateNow = Instant.now().toString();
        stringBuilderFilter.append("'");
        stringBuilderFilter.append(dateNow);
        stringBuilderFilter.append("'");

        Integer maxResults = MAX_RESULT;

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(accessToken, email);
        PagedResult<Event> events = outlookService
                .getNextEvents(sort, properties, maxResults, stringBuilderFilter.toString()).execute().body();

        return events.getValue();
    }

    /**
    *
    * @param userId .
    * @return .
    * @throws IOException .
    */
    public Event getNextEventForAllAccount(@RequestParam("userId") final String userId) throws IOException {
        AppUser user = userRepository.findByName(userId);
        List<MicrosoftAccountData> accounts = user.getAccountsMicrosoft();

        List<Event> listEvent = new ArrayList<>();
        Event event = null;
        Event eventMostRecent = null;

        for (MicrosoftAccountData accountData : accounts) {
            Event[] nextEvent = getNextEvent(accountData.getTokens().getAccessToken(), accountData.getUserEmail());
            if (nextEvent.length > 0) {
                event = nextEvent[0];
                listEvent.add(event);
            }
        }

        long dateMillisecondsMostRecent = System.currentTimeMillis();
        long dateMilliseconds;

        if (listEvent != null) {
            for (int i = 0; i < listEvent.size(); i++) {
                dateMilliseconds = listEvent.get(i).getStart().getDateTime().getTime();

                if (dateMillisecondsMostRecent < dateMilliseconds) {
                    eventMostRecent = listEvent.get(i);
                }

            }
        }

        return eventMostRecent;
    }

}
