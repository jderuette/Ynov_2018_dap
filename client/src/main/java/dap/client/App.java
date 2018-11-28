package dap.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dap.client.model.dto.EventResponse;
import dap.client.model.dto.UserResponse;
import dap.client.service.AccountService;
import dap.client.service.CalendarService;
import dap.client.service.ContactService;
import dap.client.service.GmailService;
import dap.client.service.UserService;
import dap.client.utils.DisplayHelp;

/**
 * Hello world!
 *
 */
public final class App {
    /**
     * the value to trigger the help.
     */
    private static final String HELP = "help";
    /**
     * limit of the parameter possible.
     */
    private static final int LIMIT_NUMBER_PARAMETERS = 3;
    /**
     * give the default the nbr of next event required.
     */
    private static final int DEFAULT_NB_EVENT = 1;

    /**
     * to hide the default controller.
     */
    private App() {

    }

    /**
     * provide the logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * the methode call when you will give launch the jar.
     *
     * @param args paramter given
     */
    public static void main(final String[] args) {
        if (args == null || args.length == 0) {
            DisplayHelp.callError(1);
            return;
        }
        if (args.length > LIMIT_NUMBER_PARAMETERS) {
            DisplayHelp.callError(2);
            LOGGER.error("mauvais arguments utilisés " + Arrays.toString(args));
            return;
        }

        switch (args[0]) {
        case "add":
            commandAdd(args);
            break;
        case "link":
            commandLink(args);
            break;
        case "email":
            commandEmail(args);
            break;
        case "contact":
            commandContact(args);
            break;
        case "calendar":
            commandCalendar(args);
            break;
        default:
            DisplayHelp.callError(1);
            break;
        }
    }

    /**
     * function when you use the command Calendar return help is in the parameter .
     *
     * @param args given parameter
     */
    private static void commandCalendar(final String[] args) {
        if (args[1].equalsIgnoreCase(HELP)) {
            DisplayHelp.callHelp("calendar", "userKey [nbEvent]");
        } else {
            launchCalendar(args);
        }
    }

    /**
     * function when you use the command Contact return help is in the parameter .
     *
     * @param args given parameter
     */
    private static void commandContact(final String[] args) {
        if (args[1].equalsIgnoreCase(HELP)) {
            DisplayHelp.callHelp("contact", "userKey");
        } else {
            launchContact(args);
        }
    }

    /**
     * create the new user in bdd.
     *
     * @param args gien parameter
     */
    private static void commandAdd(final String[] args) {
        if (args[1].equalsIgnoreCase(HELP)) {
            DisplayHelp.callHelp("contact", "userKey");
        } else {

            launchAdd(args);
        }
    }

    /**
     * function when you use the command Email return help is in the parameter .
     *
     * @param args given parameter
     */
    private static void commandEmail(final String[] args) {
        if (args[1].equalsIgnoreCase(HELP)) {
            DisplayHelp.callHelp("email", "userKey");
        } else {
            launchEmail(args);
        }
    }

    /**
     * function when you use the command Add return help is in the parameter .
     *
     * @param args given parameter
     */
    private static void commandLink(final String[] args) {
        if (args[1].equalsIgnoreCase(HELP)) {
            DisplayHelp.callHelp("add", "userKey & accountName");
        } else {
            launchLink(args);
        }
    }

    /**
     * methode call to get info off userKey's event.
     *
     * @param args parameters givens
     */
    private static void launchCalendar(final String[] args) {

        Integer nbEventToDisplay = DEFAULT_NB_EVENT;
        if (args.length > 2) {
            try {
                nbEventToDisplay = Integer.valueOf(args[2]);
            } catch (final NumberFormatException e) {
                DisplayHelp.callError(2);
            }
        }
        List<EventResponse> eventResponses = null;
        try {
            eventResponses = new CalendarService().getNextEvent(args[1], nbEventToDisplay);
        } catch (final IOException e) {
            System.err.println("erreur lors de la recuperation des evenement");
            LOGGER.error("erreur launchCalendar : ", e);
        }
        for (final EventResponse eventResponse : eventResponses) {
            System.out.println(eventResponse);
        }
    }

    /**
     * methode call to get info off userKey's contact.
     *
     * @param args parameters givens
     */
    private static void launchContact(final String[] args) {
        try {
            System.out.println(new ContactService().getNbrContact(args[1]));
        } catch (final IOException e) {
            System.err.println("erreur lors de l'appel des contacts");
            LOGGER.error("erreur launchContact : ", e);
        }
    }

    /**
     * methode call to get info off userKey'sEmail.
     *
     * @param args parameters givens
     */
    private static void launchEmail(final String[] args) {
        try {
            System.out.println(new GmailService().getNbrEmailUnread(args[1]));
        } catch (final IOException e) {
            LOGGER.error("erreur launchEmail : ", e);
            System.err.println("Erreur lors de la recuperation des emails");
        }
    }

    /**
     * methode call to get the authorization for the the googelAccount and link with
     * the user in Bdd.
     *
     * @param args parameters givens
     */
    private static void launchLink(final String[] args) {
        try {
            new AccountService().connexionGoogleAccount(args[1], args[2]);
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode call to create user in BDD.
     *
     * @param args parameters givens
     */

    private static void launchAdd(final String[] args) {
        if (args.length > 2) {
            DisplayHelp.callError(2);
        }
        try {
            final UserResponse addUser = new UserService().addUser(args[1]);
            System.out.println("Utilisateur crée");
            System.out.println(addUser);
        } catch (NumberFormatException | IOException e) {
            LOGGER.error("Error lors de l'ajout de l'utilisateur en BDD", e);
        }
    }

}
