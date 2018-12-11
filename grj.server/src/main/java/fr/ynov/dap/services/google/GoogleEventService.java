package fr.ynov.dap.services.google;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import fr.ynov.dap.helpers.GoogleHelper;
import fr.ynov.dap.models.common.User;
import fr.ynov.dap.models.google.GoogleAccount;
import fr.ynov.dap.repositories.UserRepository;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * CalendarService
 */
@Service
public class GoogleEventService {

    /**
     * Autowired GoogleHelper
     */
    @Autowired
    private GoogleHelper googleHelper;

    /**
     * Autowired UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LogManager.getLogger(GoogleEventService.class);

    /**
     * Return the next event
     *
     * @param userName userName to log
     * @return Event
     */
    public final fr.ynov.dap.models.common.Event getNextEvent(String userName) {

        fr.ynov.dap.models.common.Event nextEvent = new fr.ynov.dap.models.common.Event();

        try {
            User user = userRepository.findByName(userName);

            if (user == null) {
              //TODO grj by Djer |Gestion Exception| Contexte (for userName : +userName).
                //TODO grj by Djer |Log4J| une petite log ? 
                throw new Exception("User does not exists");
            }

            List<GoogleAccount> userGoogleAccountList = user.getGoogleAccountList();
            for (GoogleAccount currentGoogleAccount : userGoogleAccountList) {
                DateTime now = new DateTime(System.currentTimeMillis());

                Events nextEvents = googleHelper.getCalendarService(currentGoogleAccount.getName()).events().list("primary")
                        .setMaxResults(1)
                        .setTimeMin(now)
                        .setOrderBy("startTime")
                        .setSingleEvents(true)
                        .execute();

                fr.ynov.dap.models.common.Event nextEventResponse = new fr.ynov.dap.models.common.Event();

                if (nextEvents.getItems().size() > 0) {
                    Event event = nextEvents.getItems().get(0);

                    nextEventResponse.setName(event.getSummary());
                    //TODO grj by Djer |API Google| Attention tu ignore l'heur, c'est riqué. Comme tu va comparer sur 2 calendariers différents et que tu utilises un "is Before" si tu as 2 évènnement le même jours, celui du premier calendrier analysé sera prioritaire, même s'il est "après" dans le temps
                    //TODO grj by Djer |API Google| Attention event.getStart().**getDate()** n'est valiorisé QUE pour elms évennements durant la journée entière. Il faut utiliser "event.getStart().getDateTime()" pour les évènnements avec une heur de début et de fin
                    nextEventResponse.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(event.getStart().getDate().toString()));
                    nextEventResponse.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(event.getStart().getDate().toString()));
                  //TODO grj by Djer |API Google| Gestion de "mon" status ?
                }

                if (nextEvent.getStartDate() == null) {
                    nextEvent = nextEventResponse;
                } else {
                    if (nextEventResponse.getStartDate().before(nextEvent.getStartDate())) {
                        nextEvent = nextEventResponse;
                    }
                }
                
            }
        } catch (Exception e) {
          //TODO grj by Djer |Log4J| Contexte (for userName : +userName).
            LOGGER.error("Error when trying retrieve last event", e);
          //TODO grj by Djer |Log4J| "e.printStackTrace()" affiche directement dans la console, la pile est déja présente dans ton message de log. Supprime la ligne ci-dessous
            e.printStackTrace();
        }

        return nextEvent;
    }
}
