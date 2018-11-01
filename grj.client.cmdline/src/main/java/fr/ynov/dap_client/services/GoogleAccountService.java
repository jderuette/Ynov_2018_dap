package fr.ynov.dap_client.services;

import java.awt.*;
import java.io.*;
import java.net.*;

public class GoogleAccountService {

    private static final String DAP_API_URL = "http://localhost:8080";

    /**
     * Add an new account
     *
     * @param userKey userKey to log
     * @throws IOException Exception
     */
    public static void addAnAccount(String userKey) throws IOException {

        Desktop.getDesktop().browse(URI.create(DAP_API_URL + "/user/add/" + userKey));

    }
}
