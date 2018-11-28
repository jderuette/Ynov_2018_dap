package fr.ynov.dap.client.rest_api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Florian BRANCHEREAU
 *
 */
public class Mail extends ConnexionRestAPI {

    /**.
     * declaration du chemin
     */
    private static String chemin = "/emailNonLu?userKey=";

    /**
     * @throws URISyntaxException fonction
     * @throws IOException fonction
     */
    public Mail() throws URISyntaxException, IOException {
        super(chemin);
    }

    /**
     * @param userKey Nom du compte
     * @return Nombre de message non lu
     * @throws URISyntaxException fonction
     * @throws IOException fonction
     */
    public String getEmailNonLus(final String userKey) throws URISyntaxException, IOException {
        setParam(userKey);
        String response = recupInfo();
        return response;
    }
}
