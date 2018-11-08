package fr.ynov.dap.client.fr.ynov.client.api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Mon_PC
 * PeopleApi class get all People's fonctions
 */
public class PeopleApi extends BaseApi {
    /**.
     * default chemin of the PeopleApi
     * To change this, use SetChemin from BaseApi
     */
    private static String chemin = "/contact/";

    /**.
     * Constructor of PeopleApi and send attributs "chemin" to BaseApi
     * @throws URISyntaxException erreur lors de la création d'une nouvelle instance de PeopleApi
     * @throws IOException erreur lors de la création d'une nouvelle instance de PeopleApi
     */
    public PeopleApi() throws URISyntaxException, IOException {
        super(chemin);
    }

    /**
     * @param userKey correspondant au nom de l'utilisateur
     * @return String Number of contacts of the user
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     * @throws IOException erreur lors de l'appel à cette fonction
     */
    public String getNbContacts(final String userKey) throws URISyntaxException, IOException {
        setParam(userKey);
        return getResponseBody();
    }
}
