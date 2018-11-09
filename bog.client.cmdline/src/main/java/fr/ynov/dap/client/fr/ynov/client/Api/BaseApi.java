package fr.ynov.dap.client.fr.ynov.client.api;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * @author Mon_PC
 * This class manages request to server
 */
public class BaseApi {
    /**.
     * adresse de base de l'API
     */
    private String adresse = "http://localhost:";
    /**.
     * port de base de l'API
     */
    private String port = "8080";
    /**.
     * chemin de l'API
     */
    private String chemin;
    /**.
     * param
     */
    private String param;
    /**.
     * uri
     */
    private static URI uri;

    /**
     * @param baseChemin : calls the correct fonction to the server
     * @throws URISyntaxException erreur lors de la création d'une nouvelle instance de la classe BaseApi
     * @throws IOException erreur lors de la création d'une nouvelle instance de la classe BaseApi
     */
    public BaseApi(final String baseChemin) throws URISyntaxException, IOException {
        this.chemin = baseChemin;
    }

    /**
     * @return adresse
     * Default : http://localhost:
     */
    protected String getAdresse() {
        return adresse;
    }

    /**
     * @param newAresse
     * adresse param
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     */
    protected void setAdresse(final String newAresse) throws URISyntaxException {
        this.adresse = newAresse;
    }

    /**
     * @return String port
     * Default : 8080
     */
    protected String getPort() {
        return port;
    }

    /**
     * @param newPort
     * port param
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     */
    protected void setPort(final String newPort) throws URISyntaxException {
        this.port = newPort;
    }

    /**
     * @return chemin
     * Default : null
     */
    protected String getChemin() {
        return chemin;
    }

    /**
     * @param newChemin
     * chemin param
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     */
    protected void setChemin(final String newChemin) throws URISyntaxException {
        this.chemin = newChemin;
    }

    /**
     * @return param
     * Default : null
     */
    protected String getParam() {
        return param;
    }

    /**
     * @param newParam
     * param to set
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     */
    protected void setParam(final String newParam) throws URISyntaxException {
        this.param = newParam;
    }

    /**
     * @return responseBody
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     * @throws IOException erreur lors de l'appel à cette fonction
     * This fonction call the server with the URI configuration
     */
    protected String getResponseBody() throws URISyntaxException, IOException {
        String responseBody = "";
        try {
            updateUri();
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            InputStream os = connection.getInputStream();
            Scanner scanner = new Scanner(os);
            responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();
            connection.disconnect();
        } catch (Exception e) {
            return e.getMessage();
        }
        return responseBody;
    }

    /**.
     * This fonction open the default browser of the user
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     * @throws IOException erreur lors de l'appel à cette fonction
     */
    protected void openBrowser() throws URISyntaxException, IOException {
        try {
            updateUri();
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**.
     * This fonction update the uri
     * @throws URISyntaxException erreur lors de l'appel à cette fonction
     */
    private void updateUri() throws URISyntaxException {
        uri = new URI(adresse + port + chemin + param);
    }
}
