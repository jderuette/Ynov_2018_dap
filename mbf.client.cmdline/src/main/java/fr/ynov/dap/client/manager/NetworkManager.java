package fr.ynov.dap.client.manager;

import fr.ynov.dap.client.network.RequestHandler;
import fr.ynov.dap.client.network.WebService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * This class is a facade class. It contains all the methods which call the Api.
 */
public class NetworkManager {

    /**
     * The logger of the NetworkManager class.
     */
    private static Logger logger = Logger.getLogger(NetworkManager.class.getName());

    /**
     * This method authenticates the user with his google account on a web browser page.
     * @param userKey This is the userKey of the currently authenticated user.
     */
    public static void addUser(final String userKey){
        try {
            Desktop.getDesktop().browse(URI.create(WebService.getBaseUrl() + "/account/add/" + userKey));
        } catch (IOException e) {
            logger.severe("Failed to open the browser with the following error message: " + e.getMessage());
        }
    }

    /**
     * This method fetches the number of unread emails from the API and log the response on the console.
     * @param userKey userKey This is the userKey of the currently authenticated user.
     */
    public static void getNumberOfUnreadEmails(String userKey) {
        WebService.makeHttpCall("/email/nbUnread?userKey=" + userKey, new RequestHandler() {
            @Override
            public void uponSuccess(String response) {
                JSONObject json = new JSONObject(response);
                Integer numberOfUnreadEmails = json.getInt("messagesUnread");
                String msg = "You have " + numberOfUnreadEmails + " unread messages.";
                System.out.println(msg);
            }

            @Override
            public void uponError(Exception e) {
                logger.severe("Failed to to get the number of read emails with the following error message: " + e.getMessage());
            }
        });
    }

    /**
     * This method fetches the upcoming event from the API and log the response on the console.
     * @param userKey userKey This is the userKey of the currently authenticated user.
     */
    public static void getUpComingEvent(String userKey) {
        WebService.makeHttpCall("/event/upcomingEvent?userKey=" + userKey, new RequestHandler() {
            @Override
            public void uponSuccess(String response) {
                JSONObject json = new JSONObject(response);
                String dateKey = "date";
                if (json.has(dateKey)) {
                    logger.info("You have no upcoming event.");
                } else {
                    Date endDate = new Date(json.getJSONObject("end").getJSONObject("date").getInt("value"));
                    Date startingDate = new Date(json.getJSONObject("originalStartTime").getJSONObject("date").getInt("value"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    String msg = "Your next event is '" + json.getString("summary") +  "'. It starts on " + dateFormat.format(startingDate) + " and ends on " + dateFormat.format(endDate) + ". The status of this event is: " + json.getString("status") + ".";
                    logger.info(msg);
                }
            }
            @Override
            public void uponError(Exception e) {
                logger.severe("Failed to to get the upcoming event with the following error message: " + e.getMessage());
            }
        });
    }

    /**
     * This method fetches the number of contacts from the API and log the response on the console.
     * @param userKey userKey This is the userKey of the currently authenticated user.
     */
    public static void getNumberOfContacts(String userKey) {
        WebService.makeHttpCall("/people/nbContacts?userKey=" + userKey, new RequestHandler() {
            @Override
            public void uponSuccess(String response) {
                JSONObject json = new JSONObject(response);

                if (json.has("message")) {
                    logger.info(json.getString("message"));
                } else {
                    JSONArray connections = json.getJSONArray("connections");
                    logger.info("You have " + connections.length() + " contacts");
                }
            }

            @Override
            public void uponError(Exception e) {
                logger.severe("Failed to to get the number of contacts with the following error message: " + e.getMessage());
            }
        });
    }

}
