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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    //TODO bim by Djer Devrait être static (inutle que chaque instance ai le sien, elles auront 
    // par default la même category) 
    private final Logger logger = LogManager.getLogger();

    /**
     * public constructor.
     */
    public Controller() {

    }

    /**
     * Print number of contact of actual user.
     * @param userKey key used to request server
     * @param account gmail account (default 'me')
     */
    public final void getNbContact(final String userKey, final String account) {
        String result = getRequestResponse("contact/number/" + account + "?userKey=" + userKey);
        System.out.println("Nombre de contacts : " + result);
    }

    /**
     * Print number of unread mails in INBOX gmail.
     * @param userKey key used to request server
     * @param account gmail account (default 'me')
     */
    public final void nbUnreadMail(final String userKey, final String account) {
        String result = getRequestResponse("email/nbUnread/" + account + "?userKey=" + userKey);
        System.out.println("Nombre de messages non lu : " + result);
    }

    /**
     * Print next event informations.
     * @param userKey key used to request server
     * @param account gmail account (default 'me')
     */
    public final void getNextEvent(final String userKey, final String account) {
        String result = getRequestResponse("event/next/" + account + "?userKey=" + userKey);

        if (result == null || result.equals("")) {
            System.out.println("Aucun évènement programmé");
            return;
        }

        JsonObject json = new JsonParser().parse(result).getAsJsonObject();
        String eventName = json.get("summary").getAsString();

        JsonObject startDate = json.getAsJsonObject("start").getAsJsonObject("date");
        JsonObject endDate = json.getAsJsonObject("end").getAsJsonObject("date");
        if (startDate == null) {
            startDate = json.getAsJsonObject("start").getAsJsonObject("dateTime");
        }
        if (endDate == null) {
            endDate = json.getAsJsonObject("end").getAsJsonObject("dateTime");
        }
        Long startDateValue = startDate.get("value").getAsLong();
        Long endDateValue = endDate.get("value").getAsLong();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);
        System.out.println("Le prochain évenement, " + eventName + " commence le "
                + format.format(new Date(startDateValue)).toString() + " et termine le "
                + format.format(new Date(endDateValue)).toString() + ". \n" + "Son status actuel est \""
                + json.get("status").getAsString() + "\"");
    }

    /**
     * Add user to google account.
     * @param userKey username
     */
    public final void addUser(final String userKey) {
        URI uri;
        try {

            uri = new URI(SERVER_URL + "account/add/" + userKey);
            logger.info("Ouverture du navigateur pour la création de l'utilisateur {}", userKey);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException | IOException e) {
            //TODO bim by Djer une petite LOG error ? COntextualisée ?
            // TODO Auto-generated catch block
            //TODO bim by Djer Eviter "e.printStackTrace()" il affiche la pile dans la console. Utiliser un Logger à la place
            e.printStackTrace();
        }

    }

    /**
     * Send request to server.
     * @param url url used to send request
     * @return url result
     */
    public final String getRequestResponse(final String url) {
        URL finaleUrl;
        try {
            finaleUrl = new URL(SERVER_URL + url);
            logger.info("Consultation de l'api {}", finaleUrl);
            HttpURLConnection connection = (HttpURLConnection) finaleUrl.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(TIMEOUT_TIME);
            connection.connect();
            InputStream stream = connection.getErrorStream();

            if (stream == null) {
                stream = connection.getInputStream();
            }

            //TODO bim b ya Djer Evite les multiple return dans la même méthode ! 
            return readInputStream(stream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            //TODO bim by Djer Contextualiser la log ("pour l'URL : " + url).
            //TODO bim by Djer Utilise le deuxème paramètre pour indiquer la cause (l'exception).
            logger.error("Erreur dans la formation de l'URL");
        } catch (IOException e) {
          //TODO bim by Djer Utilise le deuxème paramètre pour indiquer la cause (l'exception).
            logger.error("Erreur, le compte google n'existe probablement pas");
            e.printStackTrace();
        }
        return "Erreur";
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
