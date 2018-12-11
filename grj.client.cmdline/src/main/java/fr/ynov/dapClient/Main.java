package fr.ynov.dapClient;

//TODO grj by Djer |IDE| Configure les "save actions" de ton IDE et évite les imports Wilcard, ca n'aporte jamais rien de bon.
import fr.ynov.dapClient.services.*;


import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {

        GmailService    gmailService    = new GmailService();
        CalendarService calendarService = new CalendarService();
        PeopleService   peopleService   = new PeopleService();

        if (args.length > 1) {
            String command = args[0];
            String userKey = args[1];

            switch (command) {
                case "addAccount":
                    GoogleAccountService.addAnAccount(userKey);
                    break;
                case "gmail":
                    System.out.println(gmailService.retrieveNumberEmailUnread(userKey));
                    break;
                case "event":
                    System.out.println(calendarService.getNextEvent(userKey));
                    break;
                case "people":
                    System.out.println(peopleService.getNumberContacts(userKey));
                    break;
                default:
                    System.out.println("Command does not exists");
                    break;
            }
        } else {
            System.out.println("User Key is missing !");
        }

    }

}
