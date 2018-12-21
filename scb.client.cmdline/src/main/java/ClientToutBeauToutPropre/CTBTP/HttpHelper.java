package ClientToutBeauToutPropre.CTBTP;
//TODO scb by Djer |POO| (ancien TO-DO) non de package ne devrait pas contenir de majuscule (au pire CamelCase)
//FIXME scb by Djer |POO| (ancien TO-DO) le package est "incomplet" devrait être du type "fr.ynov.dap.xxxxx.xxxxxx.xxxxx)
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpHelper {
	
    //TODO scb by Djer |POO| (ancien TO-DO) ce n'est pas beau de mentir à un serveur, ce client n'est PAS Mozilla !!
	private static final String USER_AGENT = "Mozilla/5.0";

	
	/**
	 * Permet d'effectuer une requete de type GET vers une URL.
	 * @return the response of the request.
	 */
	public static String ProcessGet(URL url) throws IOException {
	   	HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				return response.toString();
			} else {
				return "GET request not worked";
			}
	}
}
