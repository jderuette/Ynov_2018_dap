//TODO mbf by Djer ce package de vrait être dans un package "fr.ynov.dap.client"
package manager;

import interfaces.IWebService;
import org.json.JSONArray;
import org.json.JSONObject;
import services.network.WebService;

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
    //TODO mbf by Djer utilise le nom de la classe comme Category.
    private static Logger logger = Logger.getLogger("NetworkManager");

    /**
     * This method authenticates the user with his google account on a web browser page.
     * @param userKey This is the userKey of the currently authenticated user.
     */
    public static void addUser(final String userKey){
        try {
            Desktop.getDesktop().browse(URI.create(WebService.getBaseUrl() + "/account/add/" + userKey));
        } catch (IOException e) {
            //TODO mbf by Djer "finer" pour une erreur ?
            //TODO mbf by Djer met ton propre message en 1er argument, utilise le second argument pour indiquer la cause (l'exception), ela permet d'avoir la pile.
            logger.finer(e.getMessage());
        }
    }

    /**
     * This method fetches the number of unread emails from the API and log the response on the console.
     * @param userKey userKey This is the userKey of the currently authenticated user.
     */
    public static void getNumberOfUnreadEmails(String userKey) {
        WebService.makeHttpCall("/email/nbUnread?userKey=" + userKey, new IWebService() {
            @Override
            public void uponSuccess(String response) {
                JSONObject json = new JSONObject(response);
                Integer numberOfUnreadEmails = json.getInt("messagesUnread");
                String msg = "You have " + numberOfUnreadEmails + " unread messages.";
                //TODO mbf by Djer n'utilise pas les "logger" pour afficher des informations à l'utilisateur.
                // L'appender "console" pourrais être retiré, et du coup ton utilisateur ne verai rien.
                // LOG = pour "techos", pour les utilisateur : une View (ou Sysout en console)
                // Si le "double message" te gène, configure Log4J (tu peux rediriger la console vers "system.err",
                // ou ne plus afficher les logs dans la console (uniquement dans le fichier).
                logger.info(msg);
            }

            @Override
            public void uponError(Exception e) {
                //TODO mbk by Djer CF mon commentaire sur le catch de l'exception précedente
                logger.finer(e.getMessage());
            }
        });
    }

    /**
     * This method fetches the upcoming event from the API and log the response on the console.
     * @param userKey userKey This is the userKey of the currently authenticated user.
     */
    public static void getUpComingEvent(String userKey) {
        WebService.makeHttpCall("/event/upcomingEvent?userKey=" + userKey, new IWebService() {
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
              //TODO mbk by Djer CF mon commentaire sur le catch de l'exception précedente
                logger.finer(e.getMessage());
            }
        });
    }

    /**
     * This method fetches the number of contacts from the API and log the response on the console.
     * @param userKey userKey This is the userKey of the currently authenticated user.
     */
    public static void getNumberOfContacts(String userKey) {
        WebService.makeHttpCall("/people/nbContacts?userKey=" + userKey, new IWebService() {
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
              //TODO mbk by Djer CF mon commentaire sur le catch de l'exception précedente
                logger.finer(e.getMessage());
            }
        });
    }

}
