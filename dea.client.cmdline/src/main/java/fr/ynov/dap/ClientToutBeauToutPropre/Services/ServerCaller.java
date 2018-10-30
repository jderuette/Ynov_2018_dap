
package fr.ynov.dap.ClientToutBeauToutPropre.Services;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//TODO dea by Djer Pouquoi que tu static ? Test autres "Callers" en hérite, donc pourraient être des méthode d'instances
public abstract class ServerCaller {
    protected final static String endpoint = "http://localhost:8080";
    //TODO dea by Djer Pourrait être "final" (et en MAJUSCULE du coup)
    private static Logger logger = LogManager.getLogger();

    /**
     * Fais une requête sur l'url spécifié et renvoie le contenu sous forme de
     * string
     * 
     * @param url
     * @return
     */
    protected static String callUrl(String url) {
        URL fullUrl = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = null;

        // create the HttpURLConnection
        try {
            fullUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) fullUrl.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();
            stringBuilder = new StringBuilder();

            String line = null;
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Récupère les infos dans le stringBuilder
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (Exception e) {
            //TODO dea by Djer Evite d'afficher le message de la cause de l'erreur dans ton propre message. 
            //Utilise le deuxième parametre (l'exception qui cause le message) et laisse Log4J Gérer !
            logger.error("Une erreur est survenue lors de l'appel a l'url : " + e.getMessage());
            //TODO dea by Djer le "printStackTrace" affiche dans la console, et donc est très inneficace !
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /**
     * Ouvre le navigateur sur une addresse spécifiée
     * 
     * @param uri L'addresse url à laquelle accéder
     */
    protected static void openBrowser(URI uri) {
        try {
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            //TODO dea by Djer Evite d'afficher le message de la cause de l'erreur dans ton propre message. 
            //Utilise le deuxième parametre (l'exception qui cause le message) et laisse Log4J Gérer !
            logger.error("Une erreur est survenue lors de la tentative d'ouverture du navigateur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
