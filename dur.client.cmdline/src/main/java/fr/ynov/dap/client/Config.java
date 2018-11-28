package fr.ynov.dap.client;

public class Config {

    /**
     * stcok the instance of config.
     */
    private static Config config;

    /**
    * The default server domain: in our case we launch the app on our PC so it's localhost.
    */
    private static final String DOMAIN = "localhost";

    /**
    * The default port: it's the port where the server is started, by default it's 8080.
    */
    private static final String PORT = "8080";

    /**
    * Default url to use to discuss with server.
    */
    private static final String BASE_URL = "http://" + DOMAIN + ":" + PORT;

}
