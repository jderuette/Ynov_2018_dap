package fr.ynov.dap.client.Services;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import fr.ynov.dap.client.Config;

/**
 * The Class HttpRequestService.
 */
public class HttpRequestService {
	
	/** The http url. */
    //TODO thb by Djer Pas très utile en attribut. En varaible dans ta méthode aurait suffit,
    // et ca t'aurais éviter de faire une "fausse Javadoc"
	private String httpUrl = Config.getINSTANCE().getHTTP_URL();
	
	/**
	 * Instantiates a new http request service.
	 *
	 * @param url the url
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpRequestService(String url) throws IOException {
		URL open = new URL(httpUrl + url);
		HttpURLConnection con =  (HttpURLConnection) open.openConnection();
		
		int responseCode = con.getResponseCode();
		//TODO thb By Djer Ce n'était pas "requis" mais il était tout à fait possible d'utiliser une API de LOG...
		System.out.println("\nSending 'GET' request to URL : " + httpUrl + url);
		System.out.println("Response Code : " + responseCode);
		boolean redirect = false;
		
		int status = con.getResponseCode();
		if (status != HttpURLConnection.HTTP_OK) {
			if (status == HttpURLConnection.HTTP_MOVED_TEMP
				|| status == HttpURLConnection.HTTP_MOVED_PERM
					|| status == HttpURLConnection.HTTP_SEE_OTHER)
			redirect = true;
		}

		if (redirect) {
			String newUrl = con.getHeaderField("Location");
			try {
			    //FIXME thb by Djer si tu ouvre "le redirect" directement, tu n'aura pas la même session que pour le "add".
			    // La solution est d'ouvrir directement el "add" dans le navigauteur (pour éviter le "userID est NULL dans la session")
				Desktop.getDesktop().browse(new URI(newUrl));
				System.out.println(new URI(newUrl));
			} catch (URISyntaxException e) {
				System.err.println(e.getMessage());
			}

		}
		
		switch (responseCode) {
		case 404:
			return;
		case 500:
			return;
		default:break;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//TODO thb by Djer Renvoyer la réponse et laisser le client décider de l'affichage aurait été plus propre.
		System.out.println("REPONSE : "+response.toString());
	}
}
