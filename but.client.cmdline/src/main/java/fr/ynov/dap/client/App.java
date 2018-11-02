package fr.ynov.dap.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world.
 *
 */
public class App {

    /**
     * Name of option User.
     */
    private static final String OPTION_USER = "user";
    /**
     * Logger for the class.
     */
    private Logger logger = LogManager.getLogger();

    /**
     * Main launcher of client.
     * @param args args of command line
     */
    public static void main(final String[] args) {
        App myApp = new App();

        String command = null;
        String[] finalArgs = args;

        if (args.length > 0) {
            List<String> listArgs = new ArrayList<String>(Arrays.asList(args));
            String commandTemp = listArgs.get(listArgs.size() - 1);
            if (!commandTemp.startsWith("-")) {
                listArgs.remove(command);
                command = commandTemp;
            }
            finalArgs = listArgs.toArray(new String[0]);
        }

        //TODO but by Djer Pourquoi créer une instance, de cette même classe, pour au final apeler une méthode static ?
        myApp.run(finalArgs, command);
    }

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
        options.addOption("u", OPTION_USER, true, "specify userID");
        options.addOption("c", "calendar", true, "specify calendarID, default : primary");

        try {
            CommandLine line = parser.parse(options, optionsArgs);

            if (line.hasOption("help") || command == null) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar client.jar [args] command", options);
            }
            runCommand(command, line);
        } catch (ParseException exp) {
            //TODO but by Djer Evite de mettre le message de l'exeption dans "ton" message.
            // Utilise le deuxième argument du logger.
            // LE logger affihera le message ET la pile.
            logger.error("Unexpected exception:" + exp.getMessage());
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
        case "addAccount":
            this.addAccount(line);
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
        System.out.println("addAccount");
        System.out.println("showNextEvent");
        System.out.println("showEmailUnreadCount");
        System.out.println("showContactCount");
    }

    /**
     * Command add account Google.
     * @param line Command line for options
     */
    private void addAccount(final CommandLine line) {
        String user = line.getOptionValue(OPTION_USER);
        if (user == null) {
            logger.error("Command 'addAcount' need args 'user'");
            return;
        }
        logger.info("Run command 'addAcount' for user '" + user + "'");
        DapAPI api = DapAPI.getInstance();
        api.addUser(user);
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
