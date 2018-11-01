package fr.ynov.dap.client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.ynov.dap.client.dto.in.LoginResponseInDto;
import fr.ynov.dap.client.dto.in.NextEventInDto;
import fr.ynov.dap.client.dto.in.NumberContactInDto;
import fr.ynov.dap.client.dto.in.UnreadMailInDto;
import fr.ynov.dap.client.exception.ServerSideException;
import fr.ynov.dap.client.model.AttendeeEventStatusEnum;
import fr.ynov.dap.client.model.LoginStatusEnum;
import fr.ynov.dap.client.service.AccountService;
import fr.ynov.dap.client.service.CalendarService;
import fr.ynov.dap.client.service.ContactService;
import fr.ynov.dap.client.service.GmailService;

/**
 * Application launcher class.
 * @author Kévin Sibué
 */
public final class App {

    /**
     * Default constructor.
     */
    private App() {

    }

    /**
     * Logger instance.
     */
    static final Logger LOGGER = LogManager.getLogger();

    /**
     * Default method called on launched.
     * @param args Argument passed on console / terminal
     * Please use the following pattern to works :
     * - To add new user : "java -jar ksb-client.jar add userId"
     * - To view user's information : "java -jar ksb-client.jar [view] userId"
     * or "java -jar ksb-client.jar userId"
     */
    public static void main(final String[] args) {

        if (args.length == 1) {

            String userId = args[0];

            showUserInformations(userId);

        } else if (args.length > 1) {

            String cmd = args[0].toLowerCase();
            String userId = args[1];

            switch (cmd) {
            case "view":
                showUserInformations(userId);
                break;
            case "add":
                addUserAccount(userId);
                break;
            default:
                showError();
            }

        } else {

            showError();

        }

    }

    /**
     * Show user's informations for connected user.
     * @param userId Logged user id
     */
    private static void showUserInformations(final String userId) {

        showNumberOfUnreadMessage(userId);

        showNextEvent(userId);

        showNumberOfContact(userId);

    }

    /**
     * Show default error to user for bad parameters.
     */
    private static void showError() {

        String errorMsg = "Need more argument to run this application.\n"
                + "Please use the following pattern to works : \n"
                + "- To add new user : java -jar ksb-client.jar add <userId>\n"
                + "- To view user's information : java -jar ksb-client.jar [view] <userId>\n"
                + "\tor java -jar ksb-client.jar <userId>";

        writeErrorLine(errorMsg);

    }

    /**
     * Use AccountService to log new user.
     * @param userId User id of user.
     * @throws ServerSideException Exception returned by server
     * @throws IOException Exception
     */
    private static void addUserAccount(final String userId) {

        AccountService accSrv = new AccountService();

        try {

            LoginResponseInDto inDto = accSrv.addAccount(userId);
            LoginStatusEnum currentStatus = LoginStatusEnum.getStatusFromInt(inDto.getStatus());

            if (currentStatus == LoginStatusEnum.ERROR) {

                writeErrorLine("An error occurred during your connection. Please try again later.");

                return;

            }

            if (currentStatus == LoginStatusEnum.ALREADY_ADDED) {

                writeLine("Your account has already been added.");

            } else {

                writeLine("You will be redirect to your web browser. Please log in.");

                URI redirectUrl = new URI(inDto.getUrl());

                Desktop.getDesktop().browse(redirectUrl);

            }

        } catch (URISyntaxException e) {

            //TODO sik by Djer Comme tu Log dans writeErrrorLine, la pile de l'excpetion est "perdue".
            // Surcharge avec une méthode acceptant une exception ?
            writeErrorLine("An error occurred. Redirect URI is invalid. Please try again later : %s", e.getMessage());

        } catch (IOException e) {

            writeDefaultError(e.getMessage());

        } catch (ServerSideException e) {

            writeErrorLine("Server didn't respond or respond with an error. Please try again later : %s",
                    e.getMessage());

        }

    }

    /**
     * Use GmailService to provide number of unread mail for current logged user.
     * @param userId User id of current logged user.
     */
    private static void showNumberOfUnreadMessage(final String userId) {

        GmailService gmailSrv = new GmailService();

        try {

            UnreadMailInDto result = gmailSrv.getUnreadMail(userId);

            writeLine("You have %d unread messages.", result.getNumberOfUnreadMail());

        } catch (IOException e) {

            writeDefaultError(e.getMessage());

        } catch (ServerSideException e) {

            writeErrorLine("Server didn't respond or respond with an error. Please try again later : %s",
                    e.getMessage());

        }

    }

    /**
     * Use CalendarService to provide next event for current logged user.
     * @param userId User id of current logged user.
     */
    private static void showNextEvent(final String userId) {

        CalendarService calendarSrv = new CalendarService();

        try {

            NextEventInDto result = calendarSrv.getNextEvent(userId);

            writeLine("Next event is %s.", result.getEventSummary());

            writeLine("It will start at %tF and ending at %tF.", result.getStartingDate(), result.getEndingDate());

            AttendeeEventStatusEnum status = AttendeeEventStatusEnum.getStatusFromInt(result.getUserStatus());

            switch (status) {
            case ACCEPTED:
                writeLine("You have accepted this event.");
                break;
            case TENTATIVE:
                writeLine("You have tentatively accepted the invitation.");
                break;
            case NEEDS_ACTION:
                writeLine("This event waiting your response.");
                break;
            case DECLINED:
                writeLine("You have declined this event.");
                break;
            default:
                writeLine("Your status is unknown.");
                break;
            }

        } catch (IOException e) {

            writeDefaultError(e.getMessage());

        } catch (ServerSideException e) {

            writeErrorLine("Server didn't respond or respond with an error. Please try again later : ", e.getMessage());

        }

    }

    /**
     * Use ContactService to provide number of contact for current logged user.
     * @param userId User id of current logged user.
     */
    private static void showNumberOfContact(final String userId) {

        ContactService contactSrv = new ContactService();

        try {

            NumberContactInDto result = contactSrv.getNumberOfContact(userId);

            writeLine("You have %d contacts.", result.getNumberOfContacts());

        } catch (IOException e) {

            writeDefaultError(e.getMessage());

        } catch (ServerSideException e) {

            writeErrorLine("Server didn't respond or respond with an error. Please try again later : %s",
                    e.getMessage());

        }

    }

    /**
     * Write message on console / terminal.
     * @param msg Message to write
     * @param args Arguments for formats
     */
    private static void writeLine(final String msg, final Object... args) {
        System.out.format(msg + "\n", args);
    }

    /**
     * Write error message on console / terminal. Write on log also.
     * @param msg Message to write
     * @param args Arguments for formats
     */
    private static void writeErrorLine(final String msg, final Object... args) {

        LOGGER.error(msg);

        System.err.format(msg, args);

    }

    /**
     * Write default error message on console / terminal.
     * @param error Error to write.
     */
    private static void writeDefaultError(final String error) {

        writeErrorLine("An error occurred. Please try again later : %s", error);

    }

}
