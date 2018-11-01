package com.ynov.dap.google;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.ynov.dap.models.CalendarModel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * SERVICE FOR CALENDAR.
 * @author POL
 */
@Service
//TODO bap by Djer Déja sur le parent, pas très utile de le dupliquer ici (créé un "getEnv" en protected)
@PropertySource("classpath:config.properties")
public class CalendarService extends GoogleService {
    /** The log. */
    private Logger log = LoggerFactory.getLogger(CalendarService.class);

    /** The env. */
    @Autowired
    private Environment env;

    /**
     * Instantiates a new calendar service.
     */
    public CalendarService() {
        super();
    }

    /**
     * Result calendar.
     *
     * @param userId the user id
     * @return the calendar model
     * @throws Exception the exception
     */
    public CalendarModel resultCalendar(final String userId) throws Exception {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(userId))
                .setApplicationName(env.getProperty("application_name"))
                .build();

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        if (items.isEmpty() || items.get(0) == null) {
            //TODO bap by Djer contextualise tes messages ("for user : " + userId)
            //TODO bap by Djer pas nécéssaire de mettre "CALENDAR",si ta classe est bien nomée, on retrouve l'info dans la category
            log.info("CALENDAR : No upcoming events found.");
            return null;
        } else {
            System.out.println("Upcoming events");
          //TODO bap by Djer contextualise tes messages ("for user : " + userId)
            log.info("CALENDAR : Next upcoming events.");

            Event nextEvent = items.get(0);
            CalendarModel calendar = new CalendarModel(
                    nextEvent.getSummary(), nextEvent.getStart().getDateTime().toString(),
                    nextEvent.getEnd().getDateTime().toString(), nextEvent.getStatus());

            return calendar;
        }
    }
}
