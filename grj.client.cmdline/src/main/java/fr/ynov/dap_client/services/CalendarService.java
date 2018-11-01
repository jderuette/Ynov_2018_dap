package fr.ynov.dap_client.services;

import org.apache.logging.log4j.*;
import org.json.JSONObject;

import java.io.*;
import java.net.*;


public class CalendarService {

    private static final String DAP_API_URL = "http://localhost:8080";

    //TODO grj by Djer Evite les "espace" dans la catégory. Un bon nom de catégorie est le nom, qualifié, de la classe.
    // On utilise donc en général CalendarService.class
    private static final Logger LOG = LogManager.getLogger("Calendar Service");

    /**
     * Return the next event
     *
     * @param userKey userKey to log
     * @return The next event in String
     * @throws IOException Exception
     */
    public static String getNextEvent(String userKey) throws IOException {
        URL url = null;
        try {
            url = new URL(DAP_API_URL + "/event/" + userKey);
        } catch (MalformedURLException e) {
          //TODO grj by Djer Un message (en plus de la cause) serait utile, ne serait-ce que pour contextualiser
            LOG.error(e);
        }

        HttpURLConnection con = null;
        try {
            assert url != null;
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
          //TODO grj by Djer Message + contexte
            LOG.error(e);
        }
        try {
            assert con != null;
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
          //TODO grj by Djer Message + contexte
            LOG.error(e);
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
          //TODO grj by Djer Message + contexte
            LOG.error(e);
        }
        String        inputLine;
        StringBuilder content = new StringBuilder();
        assert in != null;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();


        JSONObject obj = new JSONObject(content.toString());

        if (obj.isNull("message")) {
            String name   = obj.get("name").toString();
            String start  = obj.get("start_date").toString();
            String end    = obj.get("end_date").toString();
            String status = obj.get("status").toString();

            return "Your next event is " + name + ". It starts " + start + " and ends " + end + " and it is " + status + ".";
        } else {
            return obj.get("message").toString();
        }

    }
}
