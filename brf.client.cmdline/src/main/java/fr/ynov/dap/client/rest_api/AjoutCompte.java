
package fr.ynov.dap.client.rest_api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Florian BRANCHEREAU
 * Création d'un compte
 */
public class AjoutCompte extends ConnexionRestAPI {

    /**.
     * declaration du chemin
     */
    private static String chemin = "/account/add/";

    /**
     * @throws URISyntaxException fonction
     * @throws IOException fonction
     */
    public AjoutCompte() throws URISyntaxException, IOException {
        super(chemin);
    }

    /**.
     * Récupération du nom et création du compte
     * @param userKey Nom du compte
     * @return un nouveau compte
     * @throws URISyntaxException fonction
     * @throws IOException fonction
     */
    public String getNouveauCompte(final String userKey) throws URISyntaxException, IOException {
        setParam(userKey);
        String response = recupInfo();
        return response;
    }
}
