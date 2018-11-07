package fr.ynov.dap.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Launcher of App.
 *
 */
public final class Launcher {
    /**
     * Logger for the class.
     */
    private static Logger logger = LogManager.getLogger();

    /**
     * Contructor of Launcher.
     */
    private Launcher() {
    }

    /**
     * Main launcher of client.
     * @param args args of command line
     */
    public static void main(final String[] args) {
        logger.info("Start application");
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

        myApp.run(finalArgs, command);
    }
}
