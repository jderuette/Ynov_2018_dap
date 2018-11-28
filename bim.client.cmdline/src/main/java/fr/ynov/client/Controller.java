package fr.ynov.client;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Controller class. Contains all possible actions.
 * @author MBILLEMAZ
 *
 */
public class Controller {

    /**
     * Server used to requests.
     */
    private static final String SERVER_URL = "http://localhost:8080/";

    /**
     * Time before timeout.
     */
    private static final int TIMEOUT_TIME = 15000;

    /**
     * log4j logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * public constructor.
     */
    public Controller() {

    }

    /**
     * Print number of contact of actual user.
     * @param userKey key used to request server
     */
    public final void getNbContact(final String userKey) {
        String result = getRequestResponse("contact/number?userKey=" + userKey);
        System.out.println("Nombre de contacts : " + result);
    }

    /**
     * Print number of unread mails in INBOX gmail.
     * @param userKey key used to request server
     */
    public final void nbUnreadMail(final String userKey) {
        String result = getRequestResponse("email/nbUnread?userKey=" + userKey);
        System.out.println("Nombre de messages non lu : " + result);
    }

    /**
     * Print next event informations.
     * @param userKey key used to request server
     */
    public final void getNextEvent(final String userKey) {
        String result = getRequestResponse("event/next?userKey=" + userKey);

        if (result == null || result.equals("")) {
            System.out.println("Aucun évènement programmé");
            return;
        }

        JsonObject json = new JsonParser().parse(result).getAsJsonObject();
        String eventName = json.get("subject").getAsString();

        String startDate = json.getAsJsonObject("start").get("dateTime").getAsString();
        String endDate = json.getAsJsonObject("end").get("dateTime").getAsString();
        String organizer = json.getAsJsonObject("organizer").getAsJsonObject("emailAddress").get("name").getAsString();

        System.out.println("Le prochain évenement, " + eventName + " est organisé par " + organizer + ", commence le "
                + startDate + " et termine le " + endDate + ".");
    }

    /**
     * Add user to google account.
     * @param userKey username
     * @param accountName account name
     */
    public final void addGoogleAccount(final String userKey, final String accountName) {
        URI uri;
        if (accountName == null) {
            LOGGER.error("Nom d'utilisateur non renseigné");
            return;
        }
        try {

            uri = new URI(SERVER_URL + "account/add/google/" + accountName + "?userKey=" + userKey);
            LOGGER.info("Ouverture du navigateur pour la création du compte google {} pour l'utilisateur {}",
                    accountName, userKey);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("Erreur lors de la création du compte " + accountName, e);
        }

    }

    /**
     * Add user to microsoft account.
     * @param userKey username
     * @param accountName account name
     */
    public final void addMicrosoftAccount(final String userKey, final String accountName) {
        URI uri;
        if (accountName == null) {
            LOGGER.error("Nom d'utilisateur non renseigné");
            return;
        }
        try {

            uri = new URI(SERVER_URL + "account/add/microsoft/" + accountName + "?userKey=" + userKey);
            LOGGER.info("Ouverture du navigateur pour la création du compte microsoft {} pour l'utilisateur {}",
                    accountName, userKey);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("Erreur lors de la création du compte " + accountName, e);
        }

    }

    /**
     * Add user to google account.
     * @param userKey username
     */
    public final void addUser(final String userKey) {
        LOGGER.info("Création de l'utilisateur {}", userKey);

        String result = getRequestResponse("user/add/" + userKey);
        System.out.println("Utilisateur crée : " + result);

    }

    /**
     * Send request to server.
     * @param url url used to send request
     * @return url result
     */
    public final String getRequestResponse(final String url) {
        URL finaleUrl;

        String result = "Erreur";
        try {
            finaleUrl = new URL(SERVER_URL + url);
            LOGGER.info("Consultation de l'api {}", finaleUrl);
            HttpURLConnection connection = (HttpURLConnection) finaleUrl.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(TIMEOUT_TIME);
            connection.connect();
            InputStream stream = connection.getErrorStream();

            if (stream == null) {
                stream = connection.getInputStream();
            }

            result = readInputStream(stream);

        } catch (MalformedURLException e) {
            LOGGER.error("Erreur dans la formation de l'URL " + url, e);
        } catch (IOException e) {
            LOGGER.error("Erreur, le compte google n'existe probablement pas", e);
        }

        return result;
    }

    /**
     * Run through input stream and return string result.
     * @param stream stream to read
     * @return stream as string
     * @throws IOException if stream is null
     */
    public final String readInputStream(final InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }

        return stringBuilder.toString();
    }
}
