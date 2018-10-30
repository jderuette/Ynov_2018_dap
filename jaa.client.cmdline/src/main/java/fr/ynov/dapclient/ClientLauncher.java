package fr.ynov.dapclient;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

/**
 * Entry point of the client application.
 */
public final class ClientLauncher {
	//TODO jaa by Djer une classe de "service" aurait permis de mieu ranger.
    /**
     * Logger used for logs.
     */
    private static Logger log = LogManager.getLogger();

    /**
     * Ip address and port of the server.
     */
    private static String address = "http://localhost:8080";
    /**
     * Timeout for HttpURLConnection.
     */
    private static final int TIMEOUT_IN_MILLISECONDS = 5000;
    /**
     * Error message, used to avoid duplication.
     */
    private static final String ERROR_MESSAGE = "Error occurred: ";

    /**
     * prevent default constructor use.
     */
    private ClientLauncher() {
    }

    /**
     * Main function.
     * @param args Arguments entered by the user.
     */
    public static void main(final String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("You have to provide arguments. Type \"help\" to show the help.");
            return;
        }

        if (args[0].equalsIgnoreCase("help")) {
            final String help = "Help:\n" + "Register a new user: [add] [userName] "
                    + "[ip:port] (by default, ip:port=http://localhost:8080)\n"

                    + "View number of unread mails: [unread] [userName] [user] "
                    + "(by default, user=me) [ip:port] (by default, ip:port=http://localhost:8080)\n"

                    + "View number of contacts: [contacts] [userName] "
                    + "[ip:port] (by default, ip:port=http://localhost:8080)\n"

                    + "View the next event: [event] [userName] [ip:port] (by default, ip:port=http://localhost:8080)";
            System.out.println(help);
            return;
        }

        if (args.length == 1) {
            System.out.println("You need to enter the userName parameter.");
            return;
        }

        final String action = args[0];
        final String userKey = args[1];

        if (action.equals("unread")) {
            String user = "me";
            if (args.length == 3) {
                user = args[2];
            }

            if (args.length == 4) {
                address = args[3];
            }

            displayNumberOfMails(userKey, user);
            return;
        }

        if (action.equals("contacts")) {
            if (args.length == 3) {
                address = args[2];
            }
            displayContactNumber(userKey);
            return;
        }

        if (action.equals("add")) {
            if (args.length == 3) {
                address = args[2];
            }
            addNewUser(userKey);
            return;
        }

        if (action.equals("event")) {
            if (args.length == 3) {
                address = args[2];
            }
            displayNextEvent(userKey);
            return;
        }
        
        //TODO jaa by Djer : que se apsse-t-il si action "non reconnue" ? 
    }

    /**
     * display unread email number.
     * @param userKey useKey used for authentication
     * @param user user
     */
    private static void displayNumberOfMails(final String userKey, final String user) {
        log.debug("displayNumberofMails called. UserKey=" + userKey);
        try {

            URL url = new URL(address + "/email/nbUnread?userKey=" + userKey + "&user=" + user);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Erreur : " + conn.getResponseCode());
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Nombre de mails :");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();
            log.debug("Output=" + output);

        } catch (MalformedURLException e) {

            System.out.println(ERROR_MESSAGE + e.getMessage());
            log.error(e);

        } catch (IOException e) {

            System.out.println(ERROR_MESSAGE + e.getMessage());
            log.error(e);
        }
    }

    /**
     * display contact number.
     * @param userKey useKey used for authentication
     */
    private static void displayContactNumber(final String userKey) {
    	//TODO jaa by Djer une grosse partie de ce code pourrait être mutualiser.
        log.debug("displayContactNumber called. UserKey=" + userKey);
        try {
            URL url = new URL(address + "/people/number?userKey=" + userKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Erreur : " + conn.getResponseCode());
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Nombre de contacts :");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();
            log.debug("Output=" + output);

        } catch (MalformedURLException e) {

            System.out.println(ERROR_MESSAGE + e.getMessage());
            log.error(e);

        } catch (IOException e) {

            System.out.println(ERROR_MESSAGE + e.getMessage());
            log.error(e);
        }
    }

    /**
     * add a new user.
     * @param userKey userkey needed for authentication for other api call.
     */
    private static void addNewUser(final String userKey) {
    	//TODO jaa by Djer une grosse partie de ce code pourrait être mutualiser.
        log.debug("addNewUser called. UserKey=" + userKey);
        try {
            URL url = new URL(address + "/account/add/" + userKey);
            Desktop.getDesktop().browse(url.toURI());
            log.debug("Browser opened at:" + url);

        } catch (MalformedURLException e) {

            System.out.println(ERROR_MESSAGE + e.getMessage());
            log.error(e);

        } catch (IOException e) {

            System.out.println(ERROR_MESSAGE + e.getMessage());
            log.error(e);

        }  catch (URISyntaxException e) {
            System.out.println(ERROR_MESSAGE + e.getMessage());
            log.error(e);
        }
    }

    /**
     * display next event informations.
     * It displays the subject, start date, end date, if you accepted/declined, if you organized the event.
     * @param userKey useKey used for authentication
     */
    private static void displayNextEvent(final String userKey) {
    	//TODO jaa by Djer une grosse partie de ce code pourrait être mutualiser.
        log.debug("displayNextEvent called. UserKey=" + userKey);
        try {
            URL url = new URL(address + "/calendar/event/next?userKey=" + userKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Erreur : " + conn.getResponseCode());
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String response = br.readLine();

            conn.disconnect();
            log.debug("Response=" + response);

            Gson gson = new Gson();
            Event[] events = gson.fromJson(response, Event[].class);

            if (events.length == 0) {
                System.out.println("Pas de prochain événement.");
                return;
            }

            Event event = events[0];

            System.out.println("Sujet : " + event.summary + "\n");

            Timestamp tsStart = new Timestamp(event.start.dateTime.value);
            Date startDate = new Date(tsStart.getTime());
            System.out.println("Date de début : " + startDate + "\n");

            Timestamp tsEnd = new Timestamp(event.end.dateTime.value);
            Date endDate = new Date(tsEnd.getTime());
            System.out.println("Date de fin : " + endDate + "\n");

            if (event.attendees == null) {
                System.out.println("Pas de status d'acceptation de l'event.\n"
            + "Vous devez inviter d'autres personnes à l'événement.\n");
                System.out.println("Vous êtes l'organisateur : oui");
                return;
            }

            List<Attendee> attendees = event.attendees;
            for (Attendee attendee : attendees) {
                if (attendee.self != null && attendee.self) {
                    Map<String, String> responseStatusTranslationMap = new HashMap<String, String>();
                    responseStatusTranslationMap.put("accepted", "accepté");
                    responseStatusTranslationMap.put("declined", "refusé");
                    responseStatusTranslationMap.put("tentative", "peut-être");
                    responseStatusTranslationMap.put("needsAction", "Vous devez répondre.");

                    Map<Boolean, String> booleanTranslationMap = new HashMap<Boolean, String>();
                    booleanTranslationMap.put(true, "oui");
                    booleanTranslationMap.put(false, "non");

                    System.out.println("Événement : "
                    + responseStatusTranslationMap.getOrDefault(attendee.responseStatus, "inconnu"));
                    System.out.println("Vous êtes l'organisateur : "
                    + booleanTranslationMap.getOrDefault(attendee.organizer, "non"));
                    break;
                }
            }


        } catch (MalformedURLException e) {

            System.out.println(ERROR_MESSAGE + e.getMessage());
            log.error(e);

        } catch (IOException e) {

            System.out.println(ERROR_MESSAGE + e.getMessage());
            log.error(e);

        }
    }

}
