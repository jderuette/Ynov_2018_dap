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
    //TODO jaa by Djer |Log4J| Devrait être final, ca n'est pas utile d'en avoir un par instance (la catégorie est par classe). Dans un "launcher" tu as peu de chance d'avoir pluisuer instance du launcher mais enleve le static et/ou final uniquement lorsque c'est nécéssaire. Ainsi tu indiques clairement ton intention
    private static Logger log = LogManager.getLogger();

    /**
     * Ip address and port of the server.
     */
    //TODO jaa by Djer |POO| Place tes constantnes d'abord. Cela te permetra d'initialiser cette varaibles avec la valeur par defaut. Puis d'utiliser QUE ta variable dans ton code (cf ma remarques sur ton "aide"). C'est notament pour cela qu'on recommande de placer les constantes en premier (puis les attributs, puis les initialisateur static, puis les constructeurs, puis les méthdoes métiers, puis les méthodes génériques (toString, hashCode,..) puis les getter/setter
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
     * Fourth argument.
     */
    private static final Integer FOURTH_ARGUMENT = 3;

    /**
     * Default server url. Used to avoid code duplication in the help text.
     */
    private static final String DEFAULT_SERVER_URL = "http://localhost:8080";

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
        //TODO jaa by Djer |POO| utiliser la constante dans ton "aide" risque dêtre confusant si l'utilisateur à changé l'URL par defaut. Ca n'est pas possible dans ton code actuel, mais si jamais tu change la façon dont on peu configurer l'URL (qui serait pris en compte, sans parser les arguments) ces messages deviendront "faux"
                    + "[ip:port] (by default, ip:port=" + DEFAULT_SERVER_URL + ")\n"

                    + "Register a new Google Account (you have to add a new user first!!): "
                    + "[addgoogle] [userName] [googleAccountName] "
                    + "[ip:port] (by default, ip:port=" + DEFAULT_SERVER_URL + ")\n"

                    + "Register a new Microsoft Account (you have to add a new user first!!): "
                    + "[addmicrosoft] [userName] [microsoftAccountName] "
                    + "[ip:port] (by default, ip:port=" + DEFAULT_SERVER_URL + ")\n"

                    + "View number of unread mails: [unread] [userName] [user] "
                    + "(by default, user=me) [ip:port] (by default, ip:port=" + DEFAULT_SERVER_URL + ")\n"

                    + "View number of contacts: [contacts] [userName] "
                    + "[ip:port] (by default, ip:port=" + DEFAULT_SERVER_URL + ")\n"

                    + "View the next event: [event] [userName] "
                    + "[ip:port] (by default, ip:port=" + DEFAULT_SERVER_URL + ")";
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
            //TODO jaa by Djer |POO| Evite les multiples return dans une même méthode. (tu risque d'en oublier, et d'afficher des messages iniapropriés, cf ma remarques plus bas)
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

        if (action.equals("addgoogle")) {
            if (args.length == FOUR_ARGUMENTS) {
                address = args[FOURTH_ARGUMENT];
            }
            String accountName = args[2];
            addNewGoogleAccount(userKey, accountName);
        }

        if (action.equals("addmicrosoft")) {
            if (args.length == FOUR_ARGUMENTS) {
                address = args[FOURTH_ARGUMENT];
            }
            String accountName = args[2];
            addNewMicrosoftAccount(userKey, accountName);
        }

        if (action.equals("event")) {
            if (args.length == THREE_ARGUMENTS) {
                address = args[2];
            }
            displayNextEvent(userKey);
            return;
        }
        
        //TODO jaa by Djer |POO| Attention se message risquede s'afficher trop souvent. Il te faudrait des "switch" au dessus (ou des else if)? Notament pour "addmicrosoft" et "addgoogle" il n'y aps de "return"
        System.out.println("Unkown parameter. Please, type \"help\" to open the help.");
    }

    /**
     * get http response from the server api.
     * @param url url to http get
     * @return reponse
     */
    private static String getResponse(final String url) {
        HttpURLConnection conn = null;
        String response = "";
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                //TODO jaa by Djer |Log4J| Une petite Log ? 
                System.out.println("Erreur : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String r;
            while ((r = br.readLine()) != null) {
                response += r;
            }

            log.debug("Reponse=" + response);

        } catch (MalformedURLException e) {

            //TODO jaa by Djer |Log4J| Pas utile le SysOut. Tu as conserver le fichier de configuration que je vous avais fourni (src/main/resources/lojg4j2.xml) donc les messages sont ajoutés dans la console (<AppenderRef ref="console" />)
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
            //TODO jaa by Djer |Rest API| Depusi qu'on a séparé user et "accounts", la route User n'a plus besoin de s'ouvrire dans le naviguateur, elle est (re)devenu un pure route d'API bien classique
            URL url = new URL(address + "/user/add/" + userKey);
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
        //TODO jaa by Djer |POO| (re) jette un oeil à la JavaDoc de @Expose. En utilisant "new Gson()" ces anotations ne sont pas utilisées. Cela ne pose pas de "bug" mais tes anotations sont inutilées
        Gson gson = new Gson();
        Event event = gson.fromJson(response, Event.class);

        if (event == null) {
            System.out.println("Pas de prochain événement.");
            return;
        }

        System.out.println("Sujet : " + event.getSummary() + "\n");

        Timestamp tsStart = new Timestamp(event.getStart().getDateTime().getValue());
        Date startDate = new Date(tsStart.getTime());
        System.out.println("Date de début : " + startDate + "\n");

        Timestamp tsEnd = new Timestamp(event.getEnd().getDateTime().getValue());
        Date endDate = new Date(tsEnd.getTime());
        System.out.println("Date de fin : " + endDate + "\n");

        if (event.getAttendees() == null) {
            System.out.println("Pas de status d'acceptation de l'event.\n");
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

    /**
     * Create a new Google Account.
     * @param userKey userkey needed for authentication for other api call.
     * @param accountName the name of the google account.
     */
    private static void addNewGoogleAccount(final String userKey, final String accountName) {
        StringBuilder st = new StringBuilder().append("Trying to add a Google Account. UserKey=")
                .append(userKey).append(" accountName=").append(accountName);
        log.debug(st.toString());
        try {
            URL url = new URL(address + "/account/add/google/" + accountName + "?userKey=" + userKey);
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
     * Create a new Microsoft Account.
     * @param userKey userkey needed for authentication for other api call.
     * @param accountName the name of the Microsoft account.
     */
    private static void addNewMicrosoftAccount(final String userKey, final String accountName) {
        //TODO jaa by Djer |POO| Evite les copier/coller, ce code es très simillaire a celui de "addNewGoogleAccount" et une des rare chose qui change, tu as oublié de le changer (le texte contient encore "Google")
        StringBuilder st = new StringBuilder().append("Trying to add a Google Account. UserKey=")
                .append(userKey).append(" accountName=").append(accountName);
        log.debug(st.toString());
        try {
            URL url = new URL(address + "/account/add/microsoft/" + accountName + "?userKey=" + userKey);
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
}
