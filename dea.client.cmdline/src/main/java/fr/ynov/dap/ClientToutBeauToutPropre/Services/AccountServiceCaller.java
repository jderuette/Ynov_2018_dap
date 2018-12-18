
package fr.ynov.dap.ClientToutBeauToutPropre.Services;

//TODO dea by Djer |IDE| Configure les "save actions" de ton IDE pour qu'il formate (et organise les imports) lors de la sauvegarde
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Classe AccountServiceCaller
 * 
 * @author antod
 *
 */
public class AccountServiceCaller extends ServerCaller
{
  /**
   * Variable utilisée pour logger
   */
    //TODO dea by Djer |Log4J| Devrait etre final. Créer un Logger est couteux et il est rarement utile d'un avoir un par instance. De plus tu n'est pas sur d'avoir un Singleton ici
  private static Logger logger = LogManager.getLogger();

  /**
   * Crée un nouveau compte
   * 
   * @param user
   */
  public static void addAccount(String user)
  {
    URI uri = null;
    try
    {
      uri = new URI(endpoint + "/account/add/" + user);
    } catch (Exception e)
    {
        //TODO dea by Djer |Log4J| Evite d'ajouter lem essage de l'exxeption à "ton" message. Utilise le deuxième paramètre (la cause) pour uy indeiquer l'excpetion et laisse Log4J faire le travaile (il indiquera le message et la pile)
      logger.error("Une erreur s'est déclenchée lors de la création d'un compte : " + e.getMessage());
      e.printStackTrace();
    }

    openBrowser(uri);
  }
}
