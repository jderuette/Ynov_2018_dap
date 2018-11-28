package fr.ynov.dap.client;

import java.io.IOException;
import java.net.URISyntaxException;

import fr.ynov.dap.client.service.RequestHttpService;

/**.
 * Launch class
 * @author Dom
 *
 */
public final class Launcher {

    /**.
     * constructor
     */
    private Launcher() {

    }

    /**.
     * requestHttpService is a variable containing the RequestHttpService;
     */
    private static RequestHttpService requestHttpService = new RequestHttpService();

    /**
     * .
     * @param args .
     * @throws IOException .
     * @throws URISyntaxException .
     */
    public static void main(final String[] args) throws IOException, URISyntaxException {
        if (args[0].equals("view")) {
            if (args[1] != null) {
                if (args[1].equals("email")) {
                    String userId = args[2];
                    requestGetString("http://localhost:8080/generalMail?userId=" + userId);
                }
            }

            if (args[1] != null) {
                if (args[1].equals("event")) {
                    String userId = args[2];
                    requestGetString("http://localhost:8080/generalEvent?userId=" + userId);
                }
            }

            if (args[1] != null) {
                if (args[1].equals("people")) {
                    String userId = args[2];
                    requestGetString("http://localhost:8080/generalContact?userId=" + userId);
                }
            }
        }

    }

    /**
    *
    * @param url .
    * @throws IOException .
    */
    public static void requestGetString(final String url) throws IOException {
        requestHttpService.executeServiceGet(new IApiResponse() {

            @Override
            public void onSuccess(final String string) {
                System.out.println("General account :" + string);
            }

            @Override
            public void onError(final IOException e) {

            }
        }, url);

    }

}
