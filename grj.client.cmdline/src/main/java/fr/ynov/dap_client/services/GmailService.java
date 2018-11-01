package fr.ynov.dap_client.services;

import org.apache.logging.log4j.*;
import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class GmailService {

    private static final String DAP_API_URL = "http://localhost:8080";

    //TODO grj by Djer Cateogry = nom, qualifié, de la classe
    private static final Logger LOG = LogManager.getLogger("Gmail Service");

    /**
     * Return the number of unread mail
     *
     * @param userKey userKey to log
     * @return String
     * @throws IOException Exception
     */
    //TODO grj by Djer Pourquoi du static ? 
    public static String retrieveNumberEmailUnread(String userKey) throws IOException {

        //TODO grj by Djer une grosse partie de ce code peut être mutualiser.
        URL url = null;
        try {
            url = new URL(DAP_API_URL + "/gmail/" + userKey);
          //TODO grj by Djer Message + contexte
        } catch (MalformedURLException e) {
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


        JSONObject obj         = new JSONObject(content.toString());
        String     emailUnread = obj.get("email_unread").toString();


        return "You have " + emailUnread + " email unread";
    }
}

