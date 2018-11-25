package fr.ynov.client;

/**
 * Hello world!
 *
 */
public class Launcher {

    /**
     * Launch application.
     * @param args Application args
     */
    public static void main(final String[] args) {
        if (args.length < 2 || args.length > 3) {
            printHelp();
        } else {
            String event = args[0];
            String user = args[1];

            String account = null;
            if (args.length == 3) {
                account = args[2];
            }

            Controller controller = new Controller();

            switch (event) {
            case "getNbUnreadMail":
                controller.nbUnreadMail(user);
                break;
            case "getNextEvent":
                controller.getNextEvent(user);
                break;
            case "getNbContact":
                controller.getNbContact(user);
                break;
            case "addUser":
                controller.addUser(user);
                break;
            case "addGoogleAccount":
                controller.addGoogleAccount(user, account);
            case "addMicrosoftAccount":
                controller.addMicrosoftAccount(user, account);
            default:
                printHelp();

            }

        }
    }

    /**
     * Print help for user.
     */
    public static void printHelp() {
        System.out.println("deux parametres sont nécessaires.\n"
                + "Parametre 1 : Action (getNbUnreadMail | getNextEvent | getNbContact | addUser | addGoogleAccount | addMicrosoftAccount)\n"
                + "Parametre 2 : Utilisateur applicatif\n" + "Parametre 3 (addAccount) : nom du compte à ajouter");
    }
}
