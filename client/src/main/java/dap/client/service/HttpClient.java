package dap.client.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import dap.client.Config;

/**
 * base service to make call.
 *
 * @author David_tepoche
 *
 */
public abstract class HttpClient {
    /**
     * return httpStatusCode Ok from server.
     */
    private static final int RESPONSE_OK_SERVEUR_STATUS_CODE = 200;

    /**
     *
     * @param url            the end of the url
     * TODO duv by Djer |JavaDoc| La "fin" d'une URL s'appel le "path" en général. Dans la Javadoc de java.net.URL (que tu utilises) : <scheme>://<authority><path>?<query>#<fragment
     * @param requestMethode If the request is a "POST" "GET"
     * @return inputStream of the response
     * @throws IOException throws if the construction of the call fail
     */
    protected String send(final String url, final String requestMethode) throws IOException {

        URL urlroot = new URL(Config.getConf().getRootUrl());
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(urlroot, getControllerRootURL() + url)
                .openConnection();
        httpConnection.setRequestMethod(requestMethode);

        if (httpConnection.getResponseCode() != RESPONSE_OK_SERVEUR_STATUS_CODE) {
            throw new ConnectException("connexion echoué, code retour :" + httpConnection.getResponseCode());
        }

        StringWriter writer = new StringWriter();
        InputStream inputStream = httpConnection.getInputStream();
        IOUtils.copy(inputStream, writer, "UTF-8");
        String valueReturned = writer.toString();
        inputStream.close();

        return valueReturned;

    }

    /**
     * provide the root url when you make a call from a child.
     *
     * @return the root url need to call the server.
     */
    abstract String getControllerRootURL();
}
