//TODO bes by Djer Evite les "underscore" dnas les nom de package. Et ecris en Camel Case : "client" est suffisant, il y a déja "dap" avant. Cette notatattion s'appel la "notation fonctionnelle inversé". Il s'agit du module "client" du projet "dap" de l'organiosation "Ynov" de "France"
package fr.ynov.dap.Client_dap;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * The Class App.
 */
public class App {

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException        Signals that an I/O exception has occurred.
     * @throws URISyntaxException the URI syntax exception
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        boolean stop = false;
        //TODO bes by Djer |IDE| Ton IDE t'indique un "leak" (une "fuite"). Il y a des cas ou tu ne "ferme" pas le Scanner. Ca n'est pas"dramatique" dans ce cas mais il faut y être vigilant.
        Scanner sc = new Scanner(System.in);

        //TODO bes by Djer |POO| Tu peux simplement écrire while(!stop). En général en JAVA on évite de repéter le "==true" (attention dans d'autre language la bonne pratique est de le "repeter")
        while (stop != true) {

            URI uri = new URI("");
            String comm = sc.nextLine();
            String[] commandes = comm.split(" ");
            
            //TODO bes by Djer |POO| Pour clarifer extrait chaque "argument" dans une variable (commandes[0] ) "role", commandes[1] "provider", commandes[2] "action", commandes[3] "userkey")

            if (commandes[0].toUpperCase().equals("ADMIN")) {
                //TODO bes by Djer |API| Hormis pour quelques cas particulier, ton client devrait consomer des "WebService", et devrait donc utiliser un "client Web", je te recommande d'utiliser "HttpURLConnection" : https://moodle.ynov.com/course/view.php?id=5062#yui_3_17_2_1_1547807746927_95
                uri = new URI("http://localhost:8080/admin");
                Desktop.getDesktop().browse(uri);
            } else if (commandes[0].toUpperCase().equals("ADD"))
                switch (commandes[1].toUpperCase()) {

                case "USER":
                    uri = new URI("http://localhost:8080/user/add/" + commandes[2]);
                    Desktop.getDesktop().browse(uri);
                    break;

                case "MICROSOFT":

                    uri = new URI(
                            "http://localhost:8080/add/microsoft/account/" + commandes[2] + "?userKey=" + commandes[3]);
                    Desktop.getDesktop().browse(uri);
                    break;

                case "GOOGLE":

                    uri = new URI(
                            "http://localhost:8080/add/google/account/" + commandes[2] + "?userKey=" + commandes[3]);
                    Desktop.getDesktop().browse(uri);
                    break;
                }

            else if (commandes[0].toUpperCase().equals("VIEW")) {
                if (commandes[1].toUpperCase().equals("ALL"))
                    switch (commandes[2].toUpperCase()) {
                    case "UNREADMAIL":
                        uri = new URI("http://localhost:8080/unreadMail/all/" + commandes[3]);
                        Desktop.getDesktop().browse(uri);
                        break;

                    case "NEXTEVENT":
                        uri = (new URI("http://localhost:8080/allEvent/" + commandes[3]));
                        Desktop.getDesktop().browse(uri);
                        break;

                    case "NBCONTACT":
                        uri = new URI("http://localhost:8080/nbContact/all/" + commandes[3]);
                        Desktop.getDesktop().browse(uri);
                        break;
                    case "MAIL":
                        uri = new URI("http://localhost:8080/all/email/" + commandes[3]);
                        Desktop.getDesktop().browse(uri);
                        break;
                    }
                else if (commandes[1].toUpperCase().equals("GOOGLE"))
                    switch (commandes[2].toUpperCase()) {
                    case "UNREADMAIL":
                        uri = new URI("http://localhost:8080/UnreadMail/google/" + commandes[3]);
                        Desktop.getDesktop().browse(uri);
                        break;
                    case "NBCONTACT":
                        uri = new URI("http://localhost:8080/nbContact/google/" + commandes[3]);
                        Desktop.getDesktop().browse(uri);
                        break;
                    case "CONTACTLIST":
                        uri = new URI("http://localhost:8080/contactList/google/" + commandes[3]);
                        Desktop.getDesktop().browse(uri);
                        break;
                    }
                else if (commandes[1].toUpperCase().equals("MICROSOFT"))
                    switch (commandes[2].toUpperCase()) {
                    case "MAIL":
                        uri = new URI("http://localhost:8080/list/email?accountName=" + commandes[3]);
                        Desktop.getDesktop().browse(uri);
                        break;

                    }
            }
        }
    }

}
