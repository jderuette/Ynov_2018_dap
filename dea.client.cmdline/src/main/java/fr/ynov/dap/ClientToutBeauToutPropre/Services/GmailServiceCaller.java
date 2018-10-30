
package fr.ynov.dap.ClientToutBeauToutPropre.Services;


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
    String result = callUrl(endpoint + "/gmail/getNbUnreadEmail/me?userKey=" + user);

    return result;
  }
}
