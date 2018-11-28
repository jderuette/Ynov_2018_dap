package fr.ynov.dap.client.fr.ynov.client.api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Mon_PC
 * AccountApi class get all Account's fonctions
 */
public class AccountApi extends BaseApi {
    /**.
     * default chemin of the AccountApi
     * To change this, use SetChemin from BaseApi
     */
    private static String chemin = "google/account/add/";

    /**.
     * Constructor of AccountApi and send attributs "chemin" to BaseApi
     * @throws URISyntaxException erreur lors de la création d'une nouvelle instance de AccountApi
     * @throws IOException erreur lors de la création d'une nouvelle instance de AccountApi
     */
    public AccountApi() throws URISyntaxException, IOException {
        super(chemin);
    }

    /**
     * @param userKey correspondant au nom de l'utilisateur
     * @return String StatusResponse of the query (null or error 500)
     * if statusresponse is null, the account can be added. Else problem with this user.
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     * @throws IOException erreur lors de l'appel à cette fonction
     */
    public String createAccount(final String userKey) throws URISyntaxException, IOException {
        setParam(userKey);
        openBrowser();
        return getResponseBody();
    }
}
