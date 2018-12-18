
package fr.ynov.dap.ClientToutBeauToutPropre;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fr.ynov.dap.ClientToutBeauToutPropre.Services.AccountServiceCaller;
import fr.ynov.dap.ClientToutBeauToutPropre.Services.CalendarServiceCaller;
import fr.ynov.dap.ClientToutBeauToutPropre.Services.GmailServiceCaller;
import fr.ynov.dap.ClientToutBeauToutPropre.Services.PeopleServiceCaller;


/**
 * Classe du launcher
 * 
 * @author antod
 *
 */
public class LauncherClient
{

  /**
   * Variable pour logger
   */
    //TODO dea by Djer |log4J| Devrait être static
  private static Logger logger = LogManager.getLogger();

  /**
   * Envoie une demande au server en tant que client
   * 
   * @param args Les arguments à fournir : [0] Nom du user. [1] Nom du service
   *             (email/calendar/people). [2] Nom de la méthode dépendant du
   *             service (getNbUnreadEmail/getUpcomingEvent/getNbContacts).
   */
  public static void main(String... args)
  {
    String loggerInfo = "";

    if (args.length >= 3 || (args.length == 2 && args[1].equals("addUser")))
    {
      String user = args[0];
      loggerInfo = "User = '" + user + "'";

      String service = args[1];
      loggerInfo += "\nService = '" + service + "'";

      String method = "";
      if (args.length >= 3)
      {
        method = args[2];
        loggerInfo += "\nMethod = '" + method + "'";
      }

      logger.info(loggerInfo);

      // Si on veut utiliser le service email
      if (service.equals("email"))
      {

        // Demande de récupérer le nombre de mails non lu
        if (method.equals("getNbUnreadEmail"))
        {
          getNbUnreadEmail(user);
        }
      }

      // Si on veut utiliser le service du calendrier
      else if (service.equals("calendar"))
      {

        // Demande de récupérer les évènements à venir
        if (method.equals("getUpcomingEvent"))
        {
          getUpcomingEvent(user);
        }
      }

      // Si on veut utiliser le service d'ajout d'utilisateur
      else if (service.equals("addUser"))
      {
          //TODO dea by Djer |POO| Ajout d'utilisateur ? (ou de compte ? )
        addAccount(user);
      }

      // Si on veut utiliser le service de contacts
      else if (service.equals("people"))
      {
        getNbContacts(user);
      }

      // Cas où le service a été mal renseigné
      else
      {
        System.out.println("Le service renseigné n'existe pas : '" + service + "'");
      }
    }
    // Si les paramètres fournis ne sont corrects
    else
    {
      System.out.println("");
      System.out.println("Méthode d'utilisation :");
      System.out.println("Paramètre 1 : Nom de l'utilisateur.");
      System.out.println("Paramètre 2 : Nom du service (email/calendar/people).");
      System.out.println(
          "Paramètre 3 : Nom de la méthode dépendant du service (getNbUnreadEmail/getUpcomingEvent/getNbContacts).");
      System.out.println("");
    }
  }

  /**
   * Demande le nombre d'emails non lus au serveur, puis les affiches dans la
   * console
   * 
   */
  private static void getNbUnreadEmail(String user)
  {
    String res = GmailServiceCaller.getNbUnreadEmail(user);
    System.out.println("");
    System.out.println("Nombre d'emails non lus : " + res);
  }

  /**
   * Demande les évènements à venir au server, puis les affiches dans la console
   * 
   */
  private static void getUpcomingEvent(String user)
  {
    String res = CalendarServiceCaller.getUpcomingEvent(user);
    System.out.println("");
    System.out.println(res);
  }

  /**
   * Demande au server de créer un nouvel utilisateur
   * 
   */
  private static void addAccount(String user)
  {
      //TODO dea by Djer |API Microsoft| Ajout de comtpe Microsoft ? 
    AccountServiceCaller.addAccount(user);
  }

  /**
   * Demande au server le nombre total de contacts, puis affiche le résultat dans
   * la console
   * 
   * @param user
   */
  private static void getNbContacts(String user)
  {
    String res = PeopleServiceCaller.getNbContacts(user);
    System.out.println("");
    System.out.println(res);
  }
}
