package fr.ynov.dap.clientapi;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Hello world!.
 */
public class App {
    /**
     * Temps avant le timeout en millisecond.
     */
    private static final Integer TIMEOUT = 15000;
    /**
     * Class main contenant le switch gerant les choix du client.
     * L'utilisateur doit lancer le jar avec 2 arguments:
     * args[0] l'action prévue
     * args[1] l'utilisateur id
     * @param args args
     * @throws IOException problème lors de l'affichage des données
     * @throws URISyntaxException problème lors de la création de l'uri
     */
    public static void main(final String[] args)
            throws IOException, URISyntaxException {
        System.out.println("api lancée");
        for (int i = 0; i < args.length; i++) {
            System.out.println("argument" + i + ": " + args[i]);
        }
        if (args.length <= 1) {
            System.out.println("pas assez d'argument");
            return;
        }
        switch (args[0]) {
        case "add":
            URL url = new URL("http://localhost:8080/account/add/" + args[1]);
            Desktop.getDesktop().browse(url.toURI());
            break;
        case "labels":
            executeURL("http://localhost:8080/Gmail/listLabel?userKey="
                    + args[1]);
        default:
        	//TODO roa by Djer Afficher un message d'errur ?
            break;
        }
        //TODO rao by Djer Il manque "nb EMail", "next Event" et "nb Contacts"
    }
    /**
     * Execute l'URL passée en argument et écrit dans la console la réponse.
     * du serveur contacté.
     * @param url l'url à atteindre
     * @throws IOException exception
     */
    private static void executeURL(final String url) throws IOException {
        URL urlCible = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)
                urlCible.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(TIMEOUT);
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            line += "\n";
            stringBuilder.append(line);
        }
        //TODO roa by Djer Il serait plus propre de renvoyer la réponse, 
        // et de laisser l'appelant décider du mode d'affichage
        System.out.println(stringBuilder.toString());
        reader.close();
    }
}
