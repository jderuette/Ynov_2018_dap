
package fr.ynov.dap.ClientToutBeauToutPropre.Services;


import java.util.Date;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * Classe CalendarServiceCaller
 * 
 * @author antod
 *
 */
public class CalendarServiceCaller extends ServerCaller
{
  /**
   * Récupère le prochaine évènement et le renvoie en string
   * 
   * @param user
   * @return
   */
  public static String getUpcomingEvent(String user)
  {
    String serverResult = callUrl(endpoint + "/calendar/getUpcomingEvent?userKey=" + user);
    String res = "Aucun evenment n'est à venir.";

    if (serverResult.length() > 0)
    {
      JsonObject json = new JsonParser().parse(serverResult).getAsJsonObject();

      if (!json.isJsonNull())
      {
        String eventName = json.get("eventName").getAsString();
        String startingDate = json.get("eventStart").getAsString();
        String endingDate = json.get("eventEnd").getAsString();

        res = String.format("Votre prochain evenement : %s, debute le %s, se termine le %s.", eventName, startingDate,
            endingDate);
      }
    }

    return res;
  }
}
