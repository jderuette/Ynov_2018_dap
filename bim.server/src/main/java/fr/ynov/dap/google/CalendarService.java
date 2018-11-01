package fr.ynov.dap.google;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.services.calendar.Calendar;

/**
 * Calendar service.
 * @author MBILLEMAZ
 *
 */

//TODO bim by Djer Faire un import plutot que de mettre le nom qualifié.
@org.springframework.stereotype.Service
public final class CalendarService extends Service {

    /**
     * Private constructor.
     */
    public CalendarService() {
        super();
    }

    /**
     * get calender service.
     * @param userKey applicative user
     * @return calendar service
     * @throws Exception if user not found
     */

    public Calendar getService(final String userKey) throws Exception {
        //TODO bim by Djer Attention "LogManager.getLogger();" est une opération couteuse.
        // Généralement on le met ne constante de la classe.
        // Ici ce n'est pas gènant, tu ne l'utilise qu'une fois, mais ne prend pas cette mauvaise habitude.
        Logger logger = LogManager.getLogger();
        logger.info("Récupération du service Calendar...");
        return new Calendar.Builder(getHttpTransport(), JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(getConfig().getApplicationName()).build();

    }

}
