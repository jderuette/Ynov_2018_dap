package fr.ynov.dap.client;

import org.json.JSONObject;

//TODO plp by Djer Evite les "*" Normalement ton IDE devrait effectuer automatiquement chaque import.
import java.awt.*;
import java.io.BufferedReader;
//TODO plp by Djer Configure les "save actions" de ton IDE. cf Mémo Eclipse (il doit y avoir une fonctionnalité simillaire sur Idea)
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

//TODO plp by Djer Les imports "static" gène souvent la lisibilitée.
import static java.awt.Desktop.getDesktop;

/**
 * @author Pierre Plessy
 */
public class App {
    /**
     * User agent
     */
    //TODO plp by Djer Ce n'est pas beau de mentir à un serveur. HttpConnection n'est PAS de Mozila.
    private static final String USER_AGENT = "Mozilla/5.0";

    /**
     *
     * @param args : args given by user
     * @throws Exception : throw exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Need argument: ");
        }

        List<String> outputs = new ArrayList<String>();
        //TODO plp by Djer Tu pourrais extraire arg[0] et [1] danas des variables pour la clareté.

        switch (args.length) {
        case 1:
            sendGet(args[0], "/email/unread");
            sendGet(args[0], "/calendar/nextEvent");
            sendGet(args[0], "/people/total");
            break;
        case 2:
            if (args[0].equals("nextEvent")) {
                sendGet(args[1], "/calendar/" + args[0]);
                return;
            }

            if (args[0].equals("unread")) {
                sendGet(args[1], "/email/" + args[0]);
                return;
            }

            if (args[0].equals("total")) {
                sendGet(args[1], "/contact/" + args[0]);
                return;
            }

            if (args[0].equals("add")) {
                //TODO plp by Djer Tu devrait surcharger la méthode. Ou passer "null" serait plus claire.
                // En général les paramètre "optionnelles" vont à la fin.
                sendGet("", "/account/add/" + args[1]);
                return;
            }

            outputs.add("Accepted functions are:");
            outputs.add("add, unread, nextEvent");
            display(outputs);
            break;
        default:
            outputs.add("Need just one or two argument(s) :");
            outputs.add("java -jar client-0.0.1-SNAPSHOT.jar 'username'");
            outputs.add("java -jar client-0.0.1-SNAPSHOT.jar add 'username'");
            outputs.add("Functions : add, unread, nextEvent, total");
            display(outputs);
            break;
        }

    }

    /**
     * Send request to the server
     * @param userKey : user of app
     * @param path : path to server
     * @throws Exception : throw exception
     */
    private static void sendGet(String userKey, String path) throws Exception {
        String params = "";
        if (!userKey.equals("")) {
            params = "?userKey=" + userKey;
        }
        URL url = new URL("http://localhost:8080" + path + params);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        //        System.out.println("GET Response Code :: " + responseCode);
        Boolean redirect = false;

        if (responseCode != HttpURLConnection.HTTP_OK) {
            if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM
                    || responseCode == HttpURLConnection.HTTP_SEE_OTHER)
                redirect = true;
        }

        if (redirect) {
            String newUrl = con.getHeaderField("Location");

            //TODO plp by Djer Ton IDE te dit que ca n'est pas utilisé. A supprimer ? bug ?
            String cookies = con.getHeaderField("Set-Cookie");

            // open the new connnection again
            //            con = (HttpURLConnection) new URL(newUrl).openConnection();
            //            con.setRequestProperty("Cookie", cookies);
            //            con.addRequestProperty("User-Agent", USER_AGENT);
            //            con.addRequestProperty("Referer", "google.com");
            getDesktop().browse(new URI(newUrl));

            System.out.println("Redirect to URL : " + newUrl);

        }

        if (responseCode == HttpURLConnection.HTTP_OK || redirect) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            JSONObject res = new JSONObject(response.toString());
            displayJson(res);
        } else {
            //TODO plp by Djer Contextualise ton message
            System.out.println("GET request not worked");
        }
    }

    /**
     * Display all list string
     * @param outputs : list string
     */
    public static void display(List<String> outputs) {
        for (String output : outputs) {
            System.out.println(output);
        }
    }

    /**
     * Display json response of server
     * @param json : JSONObject
     */
    public static void displayJson(JSONObject json) {
        for (Object key : json.keySet()) {
            String keyStr = (String) key;
            Object value = json.get(keyStr);

            System.out.println(keyStr + " : " + value);
        }
    }
}
