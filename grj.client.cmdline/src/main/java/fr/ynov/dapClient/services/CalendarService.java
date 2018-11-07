package fr.ynov.dapClient.services;

import org.apache.logging.log4j.*;
import org.json.JSONObject;

import java.io.*;
import java.net.*;


public class CalendarService {

    private static final String DAP_API_URL = "http://localhost:8080";

    private static final Logger LOG = LogManager.getLogger(CalendarService.class);

    /**
     * Return the next event
     *
     * @param userKey userKey to log
     * @return The next event in String
     * @throws IOException Exception
     */
    public String getNextEvent(String userKey) throws IOException {
        URL url = null;
        try {
            url = new URL(DAP_API_URL + "/event/" + userKey);
        } catch (MalformedURLException e) {
            LOG.error("Error when trying to create Google Api next event url", e);
        }

        HttpURLConnection con = null;
        try {
            assert url != null;
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            LOG.error("Error when trying to open connection", e);
        }
        try {
            assert con != null;
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            LOG.error("Error when trying to set request method to GET", e);
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            LOG.error("Error when trying to get input stream", e);
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
