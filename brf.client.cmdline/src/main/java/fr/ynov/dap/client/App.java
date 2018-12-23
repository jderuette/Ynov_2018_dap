package fr.ynov.dap.client;

import java.io.IOException;
import java.net.URISyntaxException;

import fr.ynov.dap.client.rest_api.AjoutCompte;
import fr.ynov.dap.client.rest_api.Contact;
import fr.ynov.dap.client.rest_api.Evenement;
import fr.ynov.dap.client.rest_api.Mail;

/**
 * @author Florian BRANCHEREAU
 * Class App contient la fonction "main" qui permet
 * de récupérer les argument et d'afficher les infos
 */
public class App {

    /**.
     * declaration du mail
     */
    private static Mail mail;
    /**.
     * declaration de l'Evenement
     */
    private static Evenement evenement;
    /**.
     * declaration des contacts
     */
    private static Contact contact;
    /**.
     * declaration du nouveaucompte
     */
    private static AjoutCompte nouveaucompte;
    /**.
     * declaration de responseMail
     */
    private static String responseMail;
    /**.
     * declaration de responseEvenement
     */
    private static String responseEvenement;
    /**.
     * declaration de responseContact
     */
    private static String responseContact;

    /**.
     * Récupération des Arguments, connexion a l'URI, récupération
     * des informations puis affichage
     * @param args argument recupere
     * @throws IOException fonction
     * @throws URISyntaxException fonction
     */
    public static void main(final String[] args) throws IOException, URISyntaxException {
        mail = new Mail();
        evenement = new Evenement();
        contact = new Contact();
        nouveaucompte = new AjoutCompte();

        String action = "";
        String userKey = "";
        boolean actionValide = false;

        switch (args.length) {
        case 1:
            userKey = args[0];
            actionValide = true;
            break;
        case 2:
            action = args[0];
            userKey = args[1];
            actionValide = true;
            break;
        default:
            break;
        }

        if (actionValide) {
            if (action.toLowerCase().equals("") || action.toLowerCase().equals("view")) {
                responseMail = mail.getEmailNonLus(userKey);
                System.out.println("Nombre de mails non lus : " + responseMail);
                responseEvenement = evenement.getNextEvent(userKey);
                System.out.println(responseEvenement);
                responseContact = contact.getNbContacts(userKey);
                System.out.println("Vous avez : " + responseContact + " contacts pour ce compte");
            } else {
                if (action.toLowerCase().equals("add")) {
                    nouveaucompte.getNouveauCompte(userKey);
                    System.out.println("Le compte : " + userKey + " a été créer");
                } else {
                    System.err.println("L'action renseigné est incorrect");
                }
                //TODO brf by Djer |API Google| Ajoute de compte Google ? 
                //TODO brf by Djer |API Microsoft| Ajoute de compte Microsoft ?
            }
        }
    }
}
