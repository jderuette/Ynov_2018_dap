
package fr.ynov.dap.ClientToutBeauToutPropre.Services;


public class PeopleServiceCaller extends ServerCaller
{
  /**
   * Demande au serveur le nombre de contacts et les renvoient sous forme de
   * String
   * 
   * @param user
   * @return
   */
  public static String getNbContacts(String user)
  {
    String result = callUrl(endpoint + "/people/getNbContacts/me?userKey=" + user);

    if (result.equals(""))
    {
      result = "0";
    }

    return String.format("Vous avez %s contact(s).", result);
  }
}
