package fr.ynov.dap.client.fr.ynov.client;
//TODO bog by Djer |POO| Attention ton nom de package est "étrange" (un double "coller" à priori)

import java.io.IOException;
import java.net.URISyntaxException;

import fr.ynov.dap.client.fr.ynov.client.api.AccountApi;
import fr.ynov.dap.client.fr.ynov.client.api.CalendarApi;
import fr.ynov.dap.client.fr.ynov.client.api.GmailApi;
import fr.ynov.dap.client.fr.ynov.client.api.PeopleApi;

/**
 * @author Mon_PC
 * Point d'entrée de l'application client
 */
public final class App {
    /**.    
     * Private constructeur de la classe App
     */
    private App() {
    }

    /**.
     * gmailRessource static variable
     */
    private static GmailApi gmailRessource;
    /**.
     * calendarRessource static variable
     */
    private static CalendarApi calendarRessource;
    /**.
     * peopleRessource static variable
     */
    private static PeopleApi peopleRessource;
    /**.
     * accountRessource static variable
     */
    private static AccountApi accountRessource;
    /**.
     * responseBodyEmail static variable
     */
    private static String responseBodyEmail;
    /**.
     * responseBodyCalendar static variable
     */
    private static String responseBodyCalendar;
    /**.
     * responseBodyPeople static varialbe
     */
    private static String responseBodyPeople;
    /**.
     * responseAddAccount static variable
     */
    private static String responseAddAccount;

    /**
     * @param args liste des arguments passés en paramètre lors du lancement de l'application
     * args contains every params typed in command line
     * @throws IOException erreur lors de l'appel à cette fonction
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     */
    public static void main(final String[] args) throws IOException, URISyntaxException {
        gmailRessource = new GmailApi();
        calendarRessource = new CalendarApi();
        peopleRessource = new PeopleApi();
        accountRessource = new AccountApi();

        final Integer sizeTable = 3;

        if (args.length > 0 && args.length < sizeTable) {
            try {
                String action = "";
                String userKey = "";
                if (args.length == 2) {
                    action = args[0].toLowerCase();
                    userKey = args[1];
                } else {
                    userKey = args[0];
                }

                if (action.equals("view") || action.equals("")) {
                    responseBodyEmail = gmailRessource.getEmailNonLus(userKey);
                    responseBodyCalendar = calendarRessource.getNextEvent(userKey);

                    /* ObjectMapper objectMapper = new ObjectMapper();
                    Event event = objectMapper.readValue(responseBodyCalendar, Event.class);
                    System.out.println("The next event is : " + event.getEventUncomming() + " the event start on :"
                            + event.getEventBegin() + " and finish at : " + event.getEventFinish() + " and you have : "
                            + event.getEventStatus() + " this event");*/

                    responseBodyPeople = peopleRessource.getNbContacts(userKey);

                    System.out.println("Mails non lus : " + responseBodyEmail);
                    System.out.println(responseBodyCalendar);
                    System.out.println("Vous avez : " + responseBodyPeople + " contact(s) pour ce compte");
                } else {
                    if (action.equals("add")) {
                        //TODO bog by Djer |POO| Comment ajouter un compte microsoft ?
                        //TODO bog by Djer |POO| Comment créer un utilisateur ?
                        responseAddAccount = accountRessource.createAccount(userKey);
                        if (responseAddAccount == null) {
                            System.out.println("Ajout du client...");
                        } else {
                            System.out.println(responseAddAccount);
                        }
                    }
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.err.println("Action ou paramètre invalide");
            }
        } else {
            System.err.println("Erreur: 1 action et 1 paramètre maximum !");
        }
    }
}
