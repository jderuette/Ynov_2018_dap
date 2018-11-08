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
     * three arguments.
     */
    private static final Integer THREE_ARGUMENTS = 3;
    /**
     * four arguments.
     */
    private static final Integer FOUR_ARGUMENTS = 4;
    /**
     * fourth argument.
     */
    private static final Integer FOURTH_ARGUMENT = 3;

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
            if (args.length == THREE_ARGUMENTS) {
                user = args[2];
            }

            if (args.length == FOUR_ARGUMENTS) {
                address = args[FOURTH_ARGUMENT];
            }

            displayNumberOfMails(userKey, user);
            return;
        }

        if (action.equals("contacts")) {
            if (args.length == THREE_ARGUMENTS) {
                address = args[2];
            }
            displayContactNumber(userKey);
            return;
        }

        if (action.equals("add")) {
            if (args.length == THREE_ARGUMENTS) {
                address = args[2];
            }
            addNewUser(userKey);
            return;
        }

        if (action.equals("event")) {
            if (args.length == THREE_ARGUMENTS) {
                address = args[2];
            }
            displayNextEvent(userKey);
            return;
        }

        System.out.println("Unkown parameter. Please, type \"help\" to open the help.");
    }

    /**
     * get http response from the server api.
     * @param url url to http get
     * @return reponse
     */
    private static String getResponse(final String url) {
        HttpURLConnection conn = null;
        String response = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Erreur : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            while ((response = br.readLine()) != null) {
                response += response;
            }

            log.debug("Reponse=" + response);

        } catch (MalformedURLException e) {

            System.out.println(ERROR_MESSAGE + e.getMessage());
            log.error(e);

        } catch (IOException e) {

            System.out.println(ERROR_MESSAGE + e.getMessage());
            log.error(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return response;
    }

    /**
     * display unread email number.
     * @param userKey useKey used for authentication
     * @param user user
     */
    private static void displayNumberOfMails(final String userKey, final String user) {
        log.debug("displayNumberofMails called. UserKey=" + userKey);
        String response = getResponse(address + "/email/nbUnread?userKey=" + userKey + "&user=" + user);
        System.out.println("Nombre de mails :");
        System.out.println(response);
    }

    /**
     * display contact number.
     * @param userKey useKey used for authentication
     */
    private static void displayContactNumber(final String userKey) {
        log.debug("displayContactNumber called. UserKey=" + userKey);
        String response = getResponse(address + "/people/number?userKey=" + userKey);
        System.out.println("Nombre de contacts :");
        System.out.println(response);
    }

    /**
     * add a new user.
     * @param userKey userkey needed for authentication for other api call.
     */
    private static void addNewUser(final String userKey) {
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
        log.debug("displayNextEvent called. UserKey=" + userKey);
        String response = getResponse(address + "/calendar/event/next?userKey=" + userKey);
        Gson gson = new Gson();
        Event[] events = gson.fromJson(response, Event[].class);

        if (events.length == 0) {
            System.out.println("Pas de prochain événement.");
            return;
        }

        Event event = events[0];

        System.out.println("Sujet : " + event.getSummary() + "\n");

        Timestamp tsStart = new Timestamp(event.getStart().getDateTime().getValue());
        Date startDate = new Date(tsStart.getTime());
        System.out.println("Date de début : " + startDate + "\n");

        Timestamp tsEnd = new Timestamp(event.getEnd().getDateTime().getValue());
        Date endDate = new Date(tsEnd.getTime());
        System.out.println("Date de fin : " + endDate + "\n");

        if (event.getAttendees() == null) {
            System.out.println("Pas de status d'acceptation de l'event.\n"
        + "Vous devez inviter d'autres personnes à l'événement.\n");
            System.out.println("Vous êtes l'organisateur : oui");
            return;
        }

        List<Attendee> attendees = event.getAttendees();
        for (Attendee attendee : attendees) {
            if (attendee.getSelf() != null && attendee.getSelf()) {
                Map<String, String> responseStatusTranslationMap = new HashMap<String, String>();
                responseStatusTranslationMap.put("accepted", "accepté");
                responseStatusTranslationMap.put("declined", "refusé");
                responseStatusTranslationMap.put("tentative", "peut-être");
                responseStatusTranslationMap.put("needsAction", "Vous devez répondre.");

                Map<Boolean, String> booleanTranslationMap = new HashMap<Boolean, String>();
                booleanTranslationMap.put(true, "oui");
                booleanTranslationMap.put(false, "non");

                System.out.println("Événement : "
                + responseStatusTranslationMap.getOrDefault(attendee.getResponseStatus(), "inconnu"));
                System.out.println("Vous êtes l'organisateur : "
                + booleanTranslationMap.getOrDefault(attendee.getOrganizer(), "non"));
                break;
            }
        }
    }

}
