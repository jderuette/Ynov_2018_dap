package fr.ynov.dap.client.rest_api;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Florian BRANCHEREAU
 * Classe Connexion_Rest_API qui permet de se connecter au
 * compte google et de donner les autorisations
 */
public abstract class ConnexionRestAPI {

    /**.
     * declaration de l'url
     */
    private String url;
    /**.
     * declaration de l'action
     */
    private String action;
    /**.
     * declaration de l'uri
     */
    private static URI uri;
    /**.
     * declaration du param
     */
    private String param;
    /**.
     * declaration du client
     */
    private OkHttpClient client = new OkHttpClient();

    /**
     * @param theAction ajout ou affichage
     * @throws URISyntaxException fonction
     * @throws IOException fonction
     */
    public ConnexionRestAPI(final String theAction) throws URISyntaxException, IOException {
        url = "http://localhost:8080";
        this.action = theAction;
        uri = new URI(url + theAction);
    }

    /**
     * @param theURL C'est l'url pour accéder aux différentes données
     * @throws URISyntaxException fonction
     */
    protected void setURL(final String theURL) throws URISyntaxException {
        this.url = theURL;
        uriAction();
    }

    /**
     * @return L'action réalisé par le client (view, add)
     */
    protected String getAction() {
        return action;
    }

    /**
     * @param theAction ajout ou affichage
     * @throws URISyntaxException fonction
     */
    protected void setAction(final String theAction) throws URISyntaxException {
        this.action = theAction;
        uriAction();
    }

    /**
     * @return Le nom du parametre (nom a créer ou qui doit récupérer les infos
     */
    protected String getParam() {
        return param;
    }

    /**
     * @param theParam les parametres rentres dans le client
     * @throws URISyntaxException fonction
     */
    protected void setParam(final String theParam) throws URISyntaxException {
        this.param = theParam;
        uriAction();
    }

    /**
     * @return Connexion et information compte google
     * @throws IOException fonction
     */
    String recupInfo() throws IOException {
        if (uri.toURL().toString().contains("account")) {
            Desktop.getDesktop().browse(uri);
        }
        Request request = new Request.Builder()
                .url(uri.toURL())
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * @throws URISyntaxException fonction
     * @throws IOException fonction
     */
    protected void openUri() throws URISyntaxException, IOException {
        try {
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * @throws URISyntaxException fonction
     */
    private void uriAction() throws URISyntaxException {
        uri = new URI(url + action + param);
    }
}
