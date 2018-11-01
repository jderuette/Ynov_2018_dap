package fr.ynov.dap.services;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import fr.ynov.dap.helpers.GoogleHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class CalendarService {

    //TODO grj by Djer Tu pourrai injecter ton Helper (en annotant ton helper avec "@Service" ou "@commponnent"
    // Cela t'éviterai dans créer un instance pour service (Gmail, Contact, ....)
    private GoogleHelper googleHelper = new GoogleHelper();


    /**
     * Return HashMap with the next event
     *
     * @param userKey userKey to log
     * @return HashMap
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    public final Map<String, String> getNextEvent(String userKey) throws GeneralSecurityException, IOException {

        DateTime now = new DateTime(System.currentTimeMillis());

        Events nextEvents = googleHelper.getCalendarService(userKey).events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        Map<String, String> nextEventResponse = new HashMap<>();

        if (nextEvents.getItems().size() > 0) {
            Event nextEvent = nextEvents.getItems().get(0);
            nextEventResponse.put("name", nextEvent.getSummary());
            nextEventResponse.put("start_date", nextEvent.getStart().getDate().toString());
            nextEventResponse.put("end_date", nextEvent.getEnd().getDate().toString());
            //TODO grj by Djer Atention status de l'évènnement, PAS de l'utilisateur
            nextEventResponse.put("status", nextEvent.getStatus());
        } else {
            nextEventResponse.put("message", "No upcoming events found.");
        }

        return nextEventResponse;
    }
}
