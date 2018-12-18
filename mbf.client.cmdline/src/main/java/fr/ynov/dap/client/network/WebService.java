package fr.ynov.dap.client.network;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * This class is used to make HTTP calls into the client App.
 */
public class WebService {

    /**
     * This getter returns the base url of the Api server.
     * @return String
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * The base url of the Api server
     */
    //TODO mbf by DJer |POO| Place des constante ne premier (ordre : Constantes, attributs, initialisateurs statics, constructeurs, méthodes métier, méthode "génériques" (toString, hashCode,..) getter/setter)
    private static final String BASE_URL = "http://localhost:8080";

    /**
     * This method is used to make HTTP calls.
     * @param endpoint The server API endpoint of the request.
     * @param callback The interface used to handle the success and failure response.
     */
    public static void makeHttpCall(String endpoint, RequestHandler callback) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            callback.uponSuccess(response.body().string());

        } catch (IOException e) {
            callback.uponError(e);
        }

    }

}
