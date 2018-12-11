package fr.ynov.dapClient.services;

import org.apache.logging.log4j.*;
import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class PeopleService {

    private static final String DAP_API_URL = "http://localhost:8080";

    private static final Logger LOG = LogManager.getLogger(PeopleService.class);

    /**
     * Return number of contacts
     *
     * @param userKey user key to log
     * @return Number of contacts in String
     * @throws IOException Exception
     */
    public String getNumberContacts(String userKey) throws IOException {
        
        //TODO grj by Djer |POO| Beaucoup de code en commun avec CalendarService.getNextEvent() ....
        URL url = null;
        try {
            url = new URL(DAP_API_URL + "/contact/" + userKey);
        } catch (MalformedURLException e) {
            LOG.error("Error when created url", e);
        }

        HttpURLConnection con = null;
        try {
            assert url != null;
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            LOG.error("Error when opening connection", e);
        }
        try {
            assert con != null;
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            LOG.error("Error when set request method to GET", e);
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            LOG.error("Error when input stream", e);
        }
        String        inputLine;
        StringBuilder content = new StringBuilder();
        assert in != null;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();


        JSONObject obj = new JSONObject(content.toString());

        if (obj.isNull("total-contacts")) {
            return "You don't have any contact...";
        } else {
            String numberContacts = obj.get("total-contacts").toString();

            return "You have " + numberContacts + " contacts.";
        }
    }
}
