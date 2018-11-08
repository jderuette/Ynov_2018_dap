package fr.ynov.dap.client.fr.ynov.client.api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Mon_PC
 * CalendarApi class get all Calendar's fonctions
 */
public class CalendarApi extends BaseApi {
    /**.
     * default chemin of the CalendarApi
     * To change this, use SetChemin from BaseApi
     */
    private static String chemin = "/calendar/event/";

    /**.
     * Constructor of CalendarApi and send attributs "chemin" to BaseApi
     * @throws URISyntaxException erreur lors de la création d'une nouvelle instance de CalendarApi
     * @throws IOException erreur lors de la création d'une nouvelle instance de CalendarApi
     */
    public CalendarApi() throws URISyntaxException, IOException {
        super(chemin);
    }

    /**
     * @param userKey correspondant au nom de l'utilisateur
     * @return GetNextEvent of the user
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     * @throws IOException erreur lors de l'appel à cette fonction
     */
    public String getNextEvent(final String userKey) throws URISyntaxException, IOException {
        setParam(userKey);
        return getResponseBody();
    }
}
