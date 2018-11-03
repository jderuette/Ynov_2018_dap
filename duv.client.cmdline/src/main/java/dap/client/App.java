package dap.client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dap.client.model.dto.AccountResponse;
import dap.client.model.dto.EventResponse;
import dap.client.service.AccountService;
import dap.client.service.CalendarService;
import dap.client.service.ContactService;
import dap.client.service.GmailService;

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
     * limit of the paramter possible.
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
            callError(1);
            return;
        }
        if (args.length > LIMIT_NUMBER_PARAMETERS) {
            callError(2);
            LOGGER.error("mauvais arguments utilisés " + Arrays.toString(args));
            return;
        }

        switch (args[0]) {
        case "add":
            commandAdd(args);
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
            callError(1);
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
            callHelp("calendar", "userId [nbEvent]");
            //TODO duv by Djer Evite les multiple retur dans la même méthode
            return;
        }
        launchCalendar(args);
    }

    /**
     * function when you use the command Contact return help is in the parameter .
     *
     * @param args given parameter
     */
    private static void commandContact(final String[] args) {
        if (args[1].equalsIgnoreCase(HELP)) {
            callHelp("contact", "userId");
            return;
        }
        launchContact(args);
    }

    /**
     * function when you use the command Email return help is in the parameter .
     *
     * @param args given parameter
     */
    private static void commandEmail(final String[] args) {
        if (args[1].equalsIgnoreCase(HELP)) {
            callHelp("email", "userId");
            return;
        }
        launchEmail(args);
    }

    /**
     * function when you use the command Add return help is in the parameter .
     *
     * @param args given parameter
     */
    private static void commandAdd(final String[] args) {
        if (args[1].equalsIgnoreCase(HELP)) {
            callHelp("add", "userId");
            return;
        }
        launchAdd(args);
    }

    /**
     * methode call to get info off UserId's event.
     *
     * @param args parameters givens
     */
    private static void launchCalendar(final String[] args) {

        Integer nbEventToDisplay = DEFAULT_NB_EVENT;
        if (args.length > 2) {
            try {
                nbEventToDisplay = Integer.valueOf(args[2]);
            } catch (NumberFormatException e) {
                callError(2);
            }
        }
        List<EventResponse> eventResponses = null;
        try {
            eventResponses = new CalendarService().getNextEvent(args[1], nbEventToDisplay);
        } catch (IOException e) {
            System.err.println("erreur lors de la recuperation des evenement");
            //TODO duv by Djer Evite de mettre le message de l'exception daans TON message.
            // Utilise le deuxième paramètre pour indiquer la "cause".
            LOGGER.error("erreur launchCalendar : " + e.getMessage());
        }
        for (EventResponse eventResponse : eventResponses) {
            System.out.println(eventResponse);
        }

    }

    /**
     * methode call to get info off UserId's contact.
     *
     * @param args parameters givens
     */
    private static void launchContact(final String[] args) {

        try {
            System.out.println(new ContactService().getNbrContact(args[1]));
        } catch (IOException e) {
            System.err.println("erreur lors de l'appel des contacts");
            LOGGER.error("erreur launchContact : " + e.getMessage());
        }

    }

    /**
     * methode call to get info off UserId'sEmail.
     *
     * @param args parameters givens
     */
    private static void launchEmail(final String[] args) {

        try {
            System.out.println(new GmailService().getNbrEmailUnread(args[1]));
        } catch (IOException e) {
            LOGGER.error("erreur launchEmail : " + e.getMessage());
            System.err.println("Erreur lors de la recuperation des emails");
        }

    }

    /**
     * methode call to get the authorization for the userID.
     *
     * @param args parameters givens
     */
    private static void launchAdd(final String[] args) {

        try {
            AccountResponse accountResponse = new AccountService().connexionGoogleAccount(args[1]);
            if (accountResponse.getRedirection() != null) {
                //TODO duv by Djer n'essaye pas de traiter la redirection.
                //Coté Server utiliser un "controller" et laisse la navigauteur géré. Le navigauteur
                // conservera le cookie !
                URI uri = new URI(accountResponse.getRedirection());
                Desktop.getDesktop().browse(uri);
            }
        } catch (IOException | URISyntaxException e) {
            LOGGER.error("erreur LauchAdd : " + e.getMessage());
            System.err.println("Erreur lors de la connection");
        }

    }

    /**
     * used to write an error in your console.
     *
     * @param level 1 determine if your error is in your command or parameter
     */
    private static void callError(final Integer level) {
        if (level == 1) {
            System.err.println("les commandes a utiliser sont : add, email, contact, calendar"
                    + "\n vous pouvez faire:| command help | pour montrer les parametres a passer dans cette commande");
        }
        if (level == 2) {
            System.err
                    .println("Vous avez passer un mauvais parametre dans la commande, taper help pour avoir de l'aide");
        }

    }

    /**
     * used to display in console the infomation required.
     *
     * @param nameCommande the commande where the help is used.
     * @param parameters   the paramters needed for this commande
     */
    private static void callHelp(final String nameCommande, final String parameters) {
        System.err.println("la commande " + nameCommande + " attend: " + parameters);
    }
}
