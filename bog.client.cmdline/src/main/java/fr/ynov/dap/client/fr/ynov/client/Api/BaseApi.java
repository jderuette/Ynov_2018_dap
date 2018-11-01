package fr.ynov.dap.client.fr.ynov.client.Api;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * 
 * @author Mon_PC
 * This class manages request to server
 */
public class BaseApi {
	
	private String adresse = "http://localhost:";
	private String port = "8080";
	private String chemin;
	private String param;
	private static URI uri;
	
	/**
	 * 
	 * @param chemin : calls the correct fonction to the server
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public BaseApi(String chemin) throws URISyntaxException, IOException
	{
		this.chemin = chemin;
	}
	/**
	 * 
	 * @return adresse
	 * Default : http://localhost:
	 */
	protected String getAdresse() {
		return adresse;
	}
	/**
	 * 
	 * @param adresse
	 * adresse param
	 * @throws URISyntaxException
	 */
	protected void setAdresse(String adresse) throws URISyntaxException {
		this.adresse = adresse;
	}
	/**
	 * 
	 * @return String port
	 * Default : 8080
	 */
	protected String getPort() {
		return port;
	}
	/**
	 * 
	 * @param port
	 * port param
	 * @throws URISyntaxException
	 */
	protected void setPort(String port) throws URISyntaxException {
		this.port = port;
	}
	/**
	 * 
	 * @return chemin
	 * Default : null
	 */
	protected String getChemin() {
		return chemin;
	}
	/**
	 * 
	 * @param chemin
	 * chemin param
	 * @throws URISyntaxException
	 */
	protected void setChemin(String chemin) throws URISyntaxException {
		this.chemin = chemin;
	}
	/**
	 * 
	 * @return param
	 * Default : null
	 */
	protected String getParam() {
		return param;
	}
	/**
	 * 
	 * @param param
	 * param to set
	 * @throws URISyntaxException
	 */
	protected void setParam(String param) throws URISyntaxException {
		this.param = param;
	}
	/**
	 * 
	 * @return responseBody
	 * @throws URISyntaxException
	 * @throws IOException
	 * This fonction call the server with the URI configuration
	 */
	protected String GetResponseBody() throws URISyntaxException, IOException
	{
		String responseBody = "";
		try 
		{ 
			UpdateUri();
	        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
	        connection.setRequestProperty("Accept-Charset", "UTF-8");
	        InputStream os = connection.getInputStream(); 
	        Scanner scanner = new Scanner(os);
	        responseBody = scanner.useDelimiter("\\A").next();
	        scanner.close();
	        connection.disconnect(); 
    	}
		catch(Exception e)
		{ 
	        return e.getMessage();
	    }
		return responseBody;
	}
	/**
	 * This fonction open the default browser of the user
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	protected void OpenBrowser() throws URISyntaxException, IOException
	{
		try
		{
			UpdateUri();
			Desktop.getDesktop().browse(uri);
		}
		catch(Exception e)
		{
			throw new IOException(e.getMessage());
		}
	}
	/**
	 * This fonction update the uri
	 * @throws URISyntaxException
	 */
	private void UpdateUri() throws URISyntaxException
	{
		uri = new URI(adresse + port + chemin + param);
	}
}