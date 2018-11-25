
package fr.ynov.dap.ClientToutBeauToutPropre.Services;


import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Classe ServerCaller
 * 
 * @author antod
 *
 */
public abstract class ServerCaller
{
  /**
   * variable utilisée pour l'endpoint vers le serveur
   */
  protected final static String endpoint = "http://localhost:8080";

  /**
   * Variable utilisée pour logger
   */
  private static Logger logger = LogManager.getLogger();

  /**
   * Fais une requête sur l'url spécifié et renvoie le contenu sous forme de
   * string
   * 
   * @param url
   * @return
   */
  protected static String callUrl(String url)
  {
    URL fullUrl = null;
    BufferedReader reader = null;
    StringBuilder stringBuilder = null;

    // create the HttpURLConnection
    try
    {
      fullUrl = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) fullUrl.openConnection();

      connection.setRequestMethod("GET");
      connection.setReadTimeout(10000);
      connection.connect();
      stringBuilder = new StringBuilder();

      String line = null;
      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      // Récupère les infos dans le stringBuilder
      while ((line = reader.readLine()) != null)
      {
        stringBuilder.append(line + "\n");
      }
    } catch (Exception e)
    {
      logger.error("Une erreur est survenue lors de l'appel a l'url : " + e.getMessage());
      e.printStackTrace();
    }

    return stringBuilder.toString();
  }

  /**
   * Ouvre le navigateur sur une addresse spécifiée
   * 
   * @param uri L'addresse url à laquelle accéder
   */
  protected static void openBrowser(URI uri)
  {
    try
    {
      Desktop.getDesktop().browse(uri);
    } catch (Exception e)
    {
      logger.error("Une erreur est survenue lors de la tentative d'ouverture du navigateur : " + e.getMessage());
      e.printStackTrace();
    }
  }
}
