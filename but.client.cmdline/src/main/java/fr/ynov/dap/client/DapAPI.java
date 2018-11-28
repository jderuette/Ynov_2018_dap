package fr.ynov.dap.client;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * @author thibault
 *
 */
public final class DapAPI {
    /**
     * API base URL.
     */
    public static final String API_PATH = "http://localhost:8080/";

    /**
     * Instance of DapAPI for singleton.
     */
    private static DapAPI instance = null;

    /**
     * Logger for the class.
     */
    private Logger logger = LogManager.getLogger();

    /**
     * Private contructor for Singleton.
     */
    private DapAPI() {
    }

    /**
     * Get instance of singleton.
     * @return the unique instance of DapAPI
     */
    public static DapAPI getInstance() {
        if (instance == null) {
            instance = new DapAPI();
        }

        return instance;
    }

    /**
     * Generate a new connection with the Dap API.
     * @param path URI Path to call.
     * @return a connection open.
     */
    private URLConnection getConnection(final String path) {
        URLConnection connection = null;
        try {
            connection = new URL(this.getFullURL(path)).openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
        } catch (IOException e) {
            logger.error("Error when fetching API path '" + path + "'", e);
        }
        return connection;
    }

    /**
     * Concatenate Path wirth Base URL of API.
     * @param path to call.
     * @return the full path URL.
     */
    public String getFullURL(final String path) {
        return API_PATH + path;
    }

    /**
     * Call API to add User.
     * @param user user to add .
     */
    public void addUser(final String user) {
        try {
            URLConnection connection = this.getConnection("user/add/" + user);
            InputStream in = connection.getInputStream();
            in.close();
            System.out.println("User créé avec succès !");
        } catch (IOException e) {
            logger.error("Error when fetching API to add user '" + user + "'", e);
        }
    }

    /**
     * Call API to add google account.
     * @param account name of google account
     * @param user user to add .
     */
    public void addGoogleAccount(final String account, final String user) {
        URI url;
        try {
            url = new URI(this.getFullURL("add/account/google/" + account + "?userKey=" + user));
            Desktop.getDesktop().browse(url);
        } catch (URISyntaxException | IOException e) {
            logger.error("Error when fetching API to add user '" + user + "'", e);
        }
    }

    /**
     * Call API to add microsoft account.
     * @param account name of microsoft account
     * @param user user to add .
     */
    public void addMicrosoftAccount(final String account, final String user) {
        URI url;
        try {
            url = new URI(this.getFullURL("add/account/microsoft/" + account + "?userKey=" + user));
            Desktop.getDesktop().browse(url);
        } catch (URISyntaxException | IOException e) {
            logger.error("Error when fetching API to add user '" + user + "'", e);
        }
    }

    /**
     * Call API to show next event in calendar.
     * @param user user of api.
     * @param calendar Calendar id of user.
     */
    public void showNextEvent(final String user, final String calendar) {
        try {
            URLConnection connection = this.getConnection("events/next?userKey=" + user + "&calendarId=" + calendar);
            InputStream in = connection.getInputStream();
            Scanner s = new Scanner(in);
            s.useDelimiter("\\A");
            String result = "";
            if (s.hasNext()) {
                result = s.next();
            }
            s.close();
            in.close();

            if (result.isEmpty()) {
                System.out.println("Aucun événement à venir =S");
                return;
            }

            JSONObject obj = new JSONObject(result);
            if (obj.has("errors")) {
                logger.error("Error when fetching next event in calendar '" + calendar + "' for user '" + user + "'",
                        obj.get("errors"));
                return;
            }
            String name = obj.getString("subject");
            String start = obj.getString("start");
            String end = obj.getString("end");
            String status = obj.getString("status");
            String organizer = "Inconnu";
            if (obj.has("organizer") && !obj.isNull("organizer")) {
                if (obj.getBoolean("organizer")) {
                    organizer = "Oui";
                } else {
                    organizer = "Non";
                }
            }

            System.out.println("Votre prochain événement : ");
            System.out.println("  Sujet : " + name);
            System.out.println("  Début : " + start);
            System.out.println("  Fin : " + end);
            System.out.println("  Suis-je l'organisateur : " + organizer);
            System.out.println("  Status : " + status);
        } catch (IOException e) {
            logger.error("Error when fetching next event in calendar '" + calendar + "' for user '" + user + "'", e);
        }
    }

    /**
     * Call API to show number of email unread.
     * @param user user of api.
     */
    public void showEmailUnreadCount(final String user) {
        try {
            URLConnection connection = this.getConnection("emails/unread?userKey=" + user);
            InputStream in = connection.getInputStream();
            Scanner s = new Scanner(in);
            s.useDelimiter("\\A");
            String result = "";
            if (s.hasNext()) {
                result = s.next();
            }
            s.close();
            in.close();
            System.out.println("Vous avez " + result + " email(s) non lu dans votre boite principale !");
        } catch (IOException e) {
            logger.error("Error when fetching email unread for user '" + user + "'", e);
        }
    }

    /**
     * Call API to show number of contact.
     * @param user user of api.
     */
    public void showContactCount(final String user) {
        try {
            URLConnection connection = this.getConnection("contact/count?userKey=" + user);
            InputStream in = connection.getInputStream();
            Scanner s = new Scanner(in);
            s.useDelimiter("\\A");
            String result = "";
            if (s.hasNext()) {
                result = s.next();
            }
            s.close();
            in.close();
            System.out.println("Vous avez " + result + " contacts !");
        } catch (IOException e) {
            logger.error("Error when fetching contacts for user '" + user + "'", e);
        }
    }
}
