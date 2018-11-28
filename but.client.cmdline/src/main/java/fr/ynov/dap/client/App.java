package fr.ynov.dap.client;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Core of app.
 *
 */
public class App {

    /**
     * Name of option User.
     */
    private static final String OPTION_USER = "user";

    /**
     * Name of option User.
     */
    private static final String OPTION_ACCOUNT = "account";

    /**
     * Logger for the class.
     */
    private Logger logger = LogManager.getLogger();

    /**
     * Function main of client.
     * @param optionsArgs arguments for command
     * @param command command to execute
     */
    public final void run(final String[] optionsArgs, final String command) {
        CommandLineParser parser = new DefaultParser();

        // create the Options
        Options options = new Options();
        options.addOption("h", "help", false, "print this message");
        options.addOption("u", OPTION_USER, true, "specify userKey");
        options.addOption("a", OPTION_ACCOUNT, true, "specify account name");
        options.addOption("c", "calendar", true, "specify calendarID, default : primary");

        try {
            CommandLine line = parser.parse(options, optionsArgs);

            if (line.hasOption("help") || command == null) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar client.jar [args] command", options);
            }
            runCommand(command, line);
        } catch (ParseException exp) {
            logger.error("Unexpected exception:", exp);
        }
    }

    /**
     * Function who dispatch command.
     * @param command The executed command
     * @param line Command line for options
     */
    private void runCommand(final String command, final CommandLine line) {
        logger.info("Receive command '" + command + "'");
        if (command == null) {
            printCommands();
            return;
        }
        switch (command) {
        case "addUser":
            this.addUser(line);
            break;
        case "addGoogleAccount":
            this.addGoogleAccount(line);
            break;
        case "addMicrosoftAccount":
            this.addMicrosoftAccount(line);
            break;
        case "showNextEvent":
            this.showNextEvent(line);
            break;
        case "showEmailUnreadCount":
            this.showEmailUnreadCount(line);
            break;
        case "showContactCount":
            this.showContactCount(line);
            break;
        default:
            logger.error("Command '" + command + "' not found");
            break;
        }
    }

    /**
     * Print alls commands availables.
     */
    private void printCommands() {
        System.out.println("Commandes disponibles : ");
        System.out.println("addUser");
        System.out.println("addGoogleAccount");
        System.out.println("addMicrosoftAccount");
        System.out.println("showNextEvent");
        System.out.println("showEmailUnreadCount");
        System.out.println("showContactCount");
    }

    /**
     * Command add user.
     * @param line Command line for options
     */
    private void addUser(final CommandLine line) {
        String user = line.getOptionValue(OPTION_USER);
        if (user == null) {
            logger.error("Command 'addUser' need args 'user'");
            return;
        }
        logger.info("Run command 'addUser' for user '" + user + "'");
        DapAPI api = DapAPI.getInstance();
        api.addUser(user);
    }

    /**
     * Command to add google account.
     * @param line Command line for options
     */
    private void addGoogleAccount(final CommandLine line) {
        String user = line.getOptionValue(OPTION_USER);
        String account = line.getOptionValue(OPTION_ACCOUNT);
        if (user == null) {
            logger.error("Command 'addGoogleAccount' need args 'user'");
            return;
        }
        if (account == null) {
            logger.error("Command 'addGoogleAccount' need args 'account'");
            return;
        }
        logger.info("Run command 'addGoogleAccount' for user '" + user + "' and account '" + account + "'");
        DapAPI api = DapAPI.getInstance();
        api.addGoogleAccount(account, user);
    }

    /**
     * Command to add google account.
     * @param line Command line for options
     */
    private void addMicrosoftAccount(final CommandLine line) {
        String user = line.getOptionValue(OPTION_USER);
        String account = line.getOptionValue(OPTION_ACCOUNT);
        if (user == null) {
            logger.error("Command 'addMicrosoftAccount' need args 'user'");
            return;
        }
        if (account == null) {
            logger.error("Command 'addMicrosoftAccount' need args 'account'");
        }
        logger.info("Run command 'addMicrosoftAccount' for user '" + user + "' and account '" + account + "'");
        DapAPI api = DapAPI.getInstance();
        api.addMicrosoftAccount(account, user);
    }

    /**
     * Command show next event.
     * @param line Command line for options
     */
    private void showNextEvent(final CommandLine line) {
        String user = line.getOptionValue(OPTION_USER);
        String calendar = line.getOptionValue("calendar");
        if (user == null) {
            logger.error("Command 'showNextEvent' need args 'user'");
            return;
        }
        if (calendar == null) {
            calendar = "primary";
        }
        logger.info("Run command 'showNextEvent' for user '" + user + "' and calendar '" + calendar + "'");
        DapAPI api = DapAPI.getInstance();
        api.showNextEvent(user, calendar);
    }

    /**
     * Command show number of emails unread.
     * @param line Command line for options
     */
    private void showEmailUnreadCount(final CommandLine line) {
        String user = line.getOptionValue(OPTION_USER);
        if (user == null) {
            logger.error("Command 'showEmailUnreadCount' need args 'user'");
            return;
        }
        logger.info("Run command 'showEmailUnreadCount' for user '" + user + "'");
        DapAPI api = DapAPI.getInstance();
        api.showEmailUnreadCount(user);
    }

    /**
     * Command show count of contacts.
     * @param line Command line for options
     */
    private void showContactCount(final CommandLine line) {
        String user = line.getOptionValue(OPTION_USER);
        if (user == null) {
            logger.error("Command 'showContactCount' need args 'user'");
            return;
        }
        logger.info("Run command 'showContactCount' for user '" + user + "'");
        DapAPI api = DapAPI.getInstance();
        api.showContactCount(user);
    }
}
