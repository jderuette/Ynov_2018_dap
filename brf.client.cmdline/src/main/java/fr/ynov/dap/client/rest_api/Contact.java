package fr.ynov.dap.client.rest_api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Florian BRANCHEREAU
 *
 */
public class Contact extends ConnexionRestAPI {

    /**.
     * declaration du chemin
     */
    private static String chemin = "/contact?userKey=";

    /**
     * @throws URISyntaxException fonction
     * @throws IOException fonction
     */
    public Contact() throws URISyntaxException, IOException {
        super(chemin);
    }

    /**
     * @param userKey Nom du compte
     * @return Le nombre de contact
     * @throws URISyntaxException fonction
     * @throws IOException fonction
     */
    public String getNbContacts(final String userKey) throws URISyntaxException, IOException {
        setParam(userKey);
        String response = recupInfo();
        return response;
    }
}
