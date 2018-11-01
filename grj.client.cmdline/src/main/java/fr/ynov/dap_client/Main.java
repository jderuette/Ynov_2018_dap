package fr.ynov.dap_client;
//TODO grj by Djer Pas de underscore dans un nom de package ! 

import fr.ynov.dap_client.services.*;


import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {

        if (args.length > 1) {
            String command = args[0];
            String userKey = args[1];

            switch (command) {
                case "addAccount":
                    GoogleAccountService.addAnAccount(userKey);
                    break;
                case "gmail":
                    System.out.println(GmailService.retrieveNumberEmailUnread(userKey));
                    break;
                case "event":
                    System.out.println(CalendarService.getNextEvent(userKey));
                    break;
                case "people":
                    System.out.println(PeopleService.getNumberContacts(userKey));
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
