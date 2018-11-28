
package fr.ynov.dap.ClientToutBeauToutPropre.Services;


/**
 * Classe GmailServiceCaller
 * 
 * @author antod
 *
 */
public class GmailServiceCaller extends ServerCaller
{
  /**
   * Récupère le nombre de mails non lus et les renvoies sous forme de String
   * 
   * @param user
   * @return
   */
  public static String getNbUnreadEmail(String user)
  {
    String result = callUrl(endpoint + "/email/getNbUnreadEmail?userKey=" + user);

    return result;
  }
}
