package fr.ynov.dap.client;

//TODO bof by Djer |IDE| Configure les "save action" de ton IDE pour éviter de laisser trainer des imports inutilisé.
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpHelper {

	/**
	 * 
	 * @param path The path that the request try to attempt
	 * @return The result of the request
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String sendGetRequest(final String path) throws IOException, URISyntaxException {
		URL obj = new URL(Config.URL_BASE + path);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", Config.USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			//TODO bof by Djer |POO| Evite les multiples return dans la même méthode
			return response.toString();
		} else {
			return "GET request not worked";
		}

	}
	
}
