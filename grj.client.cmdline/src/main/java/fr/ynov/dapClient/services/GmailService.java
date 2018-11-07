package fr.ynov.dapClient.services;

import org.apache.logging.log4j.*;
import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class GmailService {

    private static final String DAP_API_URL = "http://localhost:8080";

    private static final Logger LOG = LogManager.getLogger(GmailService.class);

    /**
     * Return the number of unread mail
     *
     * @param userKey userKey to log
     * @return String
     * @throws IOException Exception
     */
    public String retrieveNumberEmailUnread(String userKey) throws IOException {

        URL url = null;
        try {
            url = new URL(DAP_API_URL + "/gmail/" + userKey);
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

        JSONObject obj         = new JSONObject(content.toString());
        String     emailUnread = obj.get("email_unread").toString();

        return "You have " + emailUnread + " email unread";
    }
}

