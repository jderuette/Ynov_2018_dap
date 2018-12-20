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
      //TODO phd by Djer |Audit Code| Ton outils d'audit de code te conseil de chainer les appels à append()
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
  //TODO phd by Djer |Spring| @RequestParam n'est plus utile ici
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

        //TODO phd by Djer |POO| Nom de variable qui prete à confusion, il s'agit en faite de "now". Relis ton algo qui cherche le plus récent et tu veras qu'il ne fonctionne pas bien
        long dateMillisecondsMostRecent = System.currentTimeMillis();
        long dateMilliseconds;

        if (listEvent != null) {
            for (int i = 0; i < listEvent.size(); i++) {
                dateMilliseconds = listEvent.get(i).getStart().getDateTime().getTime();
                //TODO phd by DJer |POO| Ne prend en compte que les évènnemnt apres "now", et comme tu écrase, tu ne garde que le "dernier" de la liste (qui a la date du jour > "now")
                if (dateMillisecondsMostRecent < dateMilliseconds) {
                    eventMostRecent = listEvent.get(i);
                }

            }
        }

        return eventMostRecent;
    }

}
