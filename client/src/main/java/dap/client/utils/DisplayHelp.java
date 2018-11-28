package dap.client.utils;

/**
 *
 * @author David_tepoche
 *
 */
public final class DisplayHelp {

    /**
     * utils class should not have constructor.
     */
    private DisplayHelp() {

    }

    /**
     * used to write an error in your console.
     *
     * @param level 1 determine if your error is in your command or parameter
     */
    public static void callError(final Integer level) {
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
    public static void callHelp(final String nameCommande, final String parameters) {
        System.err.println("la commande " + nameCommande + " attend: " + parameters);
    }

}
