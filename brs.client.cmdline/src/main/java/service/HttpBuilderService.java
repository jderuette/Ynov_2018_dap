package service;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * The Class HttpBuilderService.
 */
public class HttpBuilderService {

	/** The Constant USER_AGENT. */
	private static final String USER_AGENT = null;

	/**
	 * Send get.
	 *
	 * @param url the url
	 * @return the string
	 * @throws Exception the exception
	 */
	public String sendGet(String url) throws Exception {

		// String url = "http://www.google.com/search?q=mkyong";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();

		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		System.out.println("Request URL ... " + url);

		boolean redirect = false;

		// normally, 3xx is redirect
		int status = con.getResponseCode();
		if (status != HttpURLConnection.HTTP_OK) {
			if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM
					|| status == HttpURLConnection.HTTP_SEE_OTHER)
				redirect = true;
		}

		System.out.println("Response Code ... " + status);

		if (redirect) {
		    //FIXME brs by Djer |API Google| Difficile de faire fonctionner cela (il faudrait "transférer" la session entre le "HttpURLConnection" et le naviguateur). Ouvre directement "add" dans le naviguateur

			// get redirect url from "location" header field
			String newUrl = con.getHeaderField("Location");

			// get the cookie if need, for login
			String cookies = con.getHeaderField("Set-Cookie");

			// open the new connnection again
			con = (HttpURLConnection) new URL(newUrl).openConnection();
			URI uri = new URI(newUrl.toString());
			con.setRequestProperty("Cookie", cookies);
			con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			con.addRequestProperty("User-Agent", "Mozilla");
			con.addRequestProperty("Referer", "google.com");
			Desktop.getDesktop().browse(uri);
			// System.out.println("Redirect to URL : " + newUrl);

		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		// System.out.println(response.toString());
		return response.toString();

	}

}
