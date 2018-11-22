package fr.ynov.dap.client;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.ynov.dap.client.dto.in.NextEventInDto;
import fr.ynov.dap.client.dto.in.NumberContactInDto;
import fr.ynov.dap.client.dto.in.UnreadMailInDto;
import fr.ynov.dap.client.exception.ServerSideException;
import fr.ynov.dap.client.model.AttendeeEventStatusEnum;
import fr.ynov.dap.client.service.AccountService;
import fr.ynov.dap.client.service.DaPAPIService;

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
    private static final Logger LOGGER = LogManager.getLogger();

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
                if (args.length >= 2) {
                    String accountName = args[2];
                    addGoogleAccount(accountName, userId);
                } else {
                    showError();
                }
                break;
            case "create":
                createNewAccount(userId);
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

        DaPAPIService dapService = new DaPAPIService();

        showNumberOfUnreadMessage(dapService, userId);

        showNextEvent(dapService, userId);

        showNumberOfContact(dapService, userId);

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

        writeErrorLine(errorMsg, null);

    }

    /**
     * Create a new user from userKey parameter.
     * @param userKey User's key
     */
    private static void createNewAccount(final String userKey) {

        AccountService accSrv = new AccountService();

        try {

            accSrv.createAccount(userKey);

            writeLine("You have created a new account !");

        } catch (IOException e) {

            writeErrorLine("An error occurred : %s", e, e.getMessage());

        } catch (ServerSideException e) {

            writeErrorLine("An error occurred : %s", e, e.getMessage());

        }

    }

    /**
     * Use AccountService to log new user.
     * @param userId User id of user.
     * @param accountName User account name.
     * @throws URISyntaxException Exception
     * @throws ServerSideException Exception returned by server
     * @throws IOException Exception
     */
    private static void addGoogleAccount(final String accountName, final String userId) {

        AccountService accSrv = new AccountService();

        try {

            accSrv.addGoogleAccount(accountName, userId);

        } catch (URISyntaxException e) {

            writeErrorLine("An error occurred. Redirect URI is invalid. Please try again later : %s", e,
                    e.getMessage());

        } catch (IOException e) {

            writeDefaultError(e.getMessage());

        } catch (ServerSideException e) {

            writeErrorLine("Server didn't respond or respond with an error. Please try again later : %s", e,
                    e.getMessage());

        }

    }

    /**
     * Use AccountService to log new user.
     * @param userId User id of user.
     * @param accountName User account name.
     * @throws URISyntaxException Exception
     * @throws ServerSideException Exception returned by server
     * @throws IOException Exception
     */
    private static void addMicrosoftAccount(final String accountName, final String userId) {

        AccountService accSrv = new AccountService();

        try {

            accSrv.addMicrosoftAccount(accountName, userId);

        } catch (URISyntaxException e) {

            writeErrorLine("An error occurred. Redirect URI is invalid. Please try again later : %s", e,
                    e.getMessage());

        } catch (IOException e) {

            writeDefaultError(e.getMessage());

        } catch (ServerSideException e) {

            writeErrorLine("Server didn't respond or respond with an error. Please try again later : %s", e,
                    e.getMessage());

        }

    }

    /**
     * Use GmailService to provide number of unread mail for current logged user.
     * @param userId User id of current logged user.
     * @param dapService Dap service to use.
     */
    private static void showNumberOfUnreadMessage(final DaPAPIService dapService, final String userId) {

        try {

            UnreadMailInDto result = dapService.getUnreadMail(userId);

            writeLine("You have %d unread messages.", result.getNumberOfUnreadMail());

        } catch (IOException e) {

            writeDefaultError(e.getMessage());

        } catch (ServerSideException e) {

            writeErrorLine("Server didn't respond or respond with an error. Please try again later : %s", e,
                    e.getMessage());

        }

    }

    /**
     * Use CalendarService to provide next event for current logged user.
     * @param userId User id of current logged user.
     * @param dapService Dap service to use.
     */
    private static void showNextEvent(final DaPAPIService dapService, final String userId) {

        try {

            NextEventInDto result = dapService.getNextEvent(userId);

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

            writeErrorLine("Server didn't respond or respond with an error. Please try again later : ", e,
                    e.getMessage());

        }

    }

    /**
     * Use ContactService to provide number of contact for current logged user.
     * @param userId User id of current logged user.
     * @param dapService Dap service to use.
     */
    private static void showNumberOfContact(final DaPAPIService dapService, final String userId) {

        try {

            NumberContactInDto result = dapService.getNumberOfContact(userId);

            writeLine("You have %d contacts.", result.getNumberOfContacts());

        } catch (IOException e) {

            writeDefaultError(e.getMessage());

        } catch (ServerSideException e) {

            writeErrorLine("Server didn't respond or respond with an error. Please try again later : %s", null,
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
     * @param exception Exception that occurred
     */
    private static void writeErrorLine(final String msg, final Exception exception, final Object... args) {

        if (exception == null) {
            LOGGER.error(msg);
        } else {
            LOGGER.error(msg, exception);
        }

        System.err.format(msg, args);

    }

    /**
     * Write default error message on console / terminal.
     * @param error Error to write.
     */
    private static void writeDefaultError(final String error) {

        writeErrorLine("An error occurred. Please try again later : %s", null, error);

    }

}
