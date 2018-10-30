
package fr.ynov.dap.ClientToutBeauToutPropre.Services;


import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AccountServiceCaller extends ServerCaller
{
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
    } catch (Exception e) //TODO dea by Djer tu devrais catcher une Exception plus précise.
    {
      logger.error("Une erreur s'est déclenchée lors de la création d'un compte : " + e.getMessage());
      e.printStackTrace();
    }

    openBrowser(uri);
  }
}
