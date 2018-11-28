package fr.ynov.dap.client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pierre Plessy
 */
public class App {
    /**
     *
     * @param args : args given by user
     * @throws Exception : throw exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Need argument: ");
        }

        String path = args[0];
        String userKey = args[1];

        List<String> outputs = new ArrayList<String>();

        switch (args.length) {
        case 1:
            sendGet(path, "/google/email/unread");
            sendGet(path, "/google/calendar/nextEvent");
            sendGet(path, "/google/people/total");
            break;
        case 2:
            if (path.equals("nextEvent")) {
                sendGet("/google/calendar/" + path, userKey);
                return;
            }

            if (path.equals("unread")) {
                sendGet("/google/email/" + path, userKey);
                return;
            }

            if (path.equals("total")) {
                sendGet("/google/contact/" + path, userKey);
                return;
            }

            if (path.equals("add")) {
                sendGet("/add/account/" + userKey, null);
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
     * @param path : path to server
     * @param userKey : user of app
     * @throws Exception : throw exception
     */
    private static void sendGet(String path, String userKey) throws Exception {
        String params = "";
        if (!userKey.equals(null)) {
            params = "?userKey=" + userKey;
        }
        URL url = new URL("http://localhost:8080" + path + params);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

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
            System.out.println("Server response with " + responseCode);
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
