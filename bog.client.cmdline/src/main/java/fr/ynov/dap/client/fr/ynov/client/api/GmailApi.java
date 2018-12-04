//TODO bog by Djer |POO| Attention aux majuscules dans ton nom de package (on utilise le camelCase dans les nom de package donc pas de majuscule au "début" du mot)
package fr.ynov.dap.client.fr.ynov.client.api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Mon_PC
 * GmailApi class get all Gmail's fonctions
 */
//TODO bog by Djer |JavaDoc| Le nom de cette classe (et sa Javadoc) n'est plus correcte car intéroge aussi les comptes "Microsoft"
public class GmailApi extends BaseApi {
    /**.
     * default chemin of the GmailApi
     * To change this, use SetChemin from BaseApi
     */
    private static String chemin = "/mail/global/";

    /**.
     * Constructor of GmailApi and send attributs "chemin" to BaseApi
     * @throws URISyntaxException erreur lors de la création d'une nouvelle instance de GmailApi
     * @throws IOException erreur lors de la création d'une nouvelle instance de GmailApi
     */
    public GmailApi() throws URISyntaxException, IOException {
        super(chemin);
    }

    /**
     * @param  userKey correspondant au nom de l'utilisateur
     * @return String GetUnreadMails of the user
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     * @throws IOException erreur lors de l'appel à cette fonction
     */
    public String getEmailNonLus(final String userKey) throws URISyntaxException, IOException {
        setParam(userKey);
        return getResponseBody();
    }
}
