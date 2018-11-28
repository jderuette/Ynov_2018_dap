package fr.ynov.dap.client.rest_api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Florian BRANCHEREAU
 *
 */
public class Evenement extends ConnexionRestAPI {

    /**.
     * declaration du chemin
     */
    private static String chemin = "/calendarNextEvent?userKey=";

    /**
     * @throws URISyntaxException fonction
     * @throws IOException fonction
     */
    public Evenement() throws URISyntaxException, IOException {
        super(chemin);
    }

    /**
     * @param userKey Nom du compte
     * @return Le prochain évènement
     * @throws URISyntaxException fonction
     * @throws IOException fonction
     */
    public String getNextEvent(final String userKey) throws URISyntaxException, IOException {
        setParam(userKey);
        String response = recupInfo();
        return response;
    }
}
