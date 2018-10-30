
package fr.ynov.dap.ClientToutBeauToutPropre.Services;


import java.util.Date;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


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
    String serverResult = callUrl(endpoint + "/calendar/getUpcomingEvent/me?userKey=" + user);
    String res = "Aucun evenment n'est à venir.";

    if (serverResult.length() > 0)
    {
      JsonObject json = new JsonParser().parse(serverResult).getAsJsonObject();

      if (!json.isJsonNull())
      {
        String eventName = json.get("summary").getAsString();

        String startingDate = new Date(json.getAsJsonObject("start").getAsJsonObject("date").get("value").getAsLong())
            .toString();

        String endingDate = new Date(json.getAsJsonObject("end").getAsJsonObject("date").get("value").getAsLong())
            .toString();

        //TODO dea by Djer Attention status de l'Event, pas de celui de l'utilisateur qui effectue l'appel !
        String status = json.get("status").getAsString();

        res = String.format(
            "Votre prochain evenement : %s, debute le %s, se termine le %s. Status de l'evenement : %s.", eventName,
            startingDate, endingDate, status);
      }
    }

    return res;
  }
}
