package fr.ynov.dap.services;
//TODO mot by Djer devrait être un sous package de fr.ynov.dap
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;

/**
 * The Class HttpRequestService.
 */
public class HttpRequestService {
	
	/**
	 * Instantiates a new http request service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public HttpRequestService() throws IOException, GeneralSecurityException {
		
	}
	
	/**
	 * Gets the result.
	 *
	 * @param url the url
	 * @return the result
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getResult(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		boolean redirect = false;

		// normally, 3xx is redirect
		int status = con.getResponseCode();
		if (status != HttpURLConnection.HTTP_OK) {
			if (status == HttpURLConnection.HTTP_MOVED_TEMP
				|| status == HttpURLConnection.HTTP_MOVED_PERM
					|| status == HttpURLConnection.HTTP_SEE_OTHER)
			redirect = true;
		}

		System.out.println("Response Code ... " + status);

		if (redirect) {

			// get redirect url from "location" header field
			String newUrl = con.getHeaderField("Location");
			
			try {
				Desktop.getDesktop().browse(new URI(newUrl));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// open the new connnection again
			con = (HttpURLConnection) new URL(newUrl).openConnection();
			con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			con.addRequestProperty("User-Agent", "Mozilla");
			con.addRequestProperty("Referer", "google.com");
									
			System.out.println("Redirect to URL : " + newUrl);

		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//StringBuffer response = new StringBuffer();
		
		return response.toString();
		
	}
	
}
