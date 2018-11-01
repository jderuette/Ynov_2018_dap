package fr.ynov.dap_client.services;

import org.apache.logging.log4j.*;
import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class PeopleService {

    private static final String DAP_API_URL = "http://localhost:8080";

  //TODO grj by Djer Cateogry = nom, qualifié, de la classe
    private static final Logger LOG = LogManager.getLogger("People Service");

    /**
     * Return number of contacts
     *
     * @param userKey user key to log
     * @return Number of contacts in String
     * @throws IOException Exception
     */
    public static String getNumberContacts(String userKey) throws IOException {
        URL url = null;
        try {
            url = new URL(DAP_API_URL + "/people/" + userKey);
        } catch (MalformedURLException e) {
          //TODO grj by Djer Message + contexte
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

        if (obj.isNull("number_contact")) {
            return "You don't have any contact...";
        } else {
            String numberContacts = obj.get("number_contact").toString();

            return "You have " + numberContacts + " contacts.";
        }
    }
}
