package fr.ynov.dap.client.rest_api;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 
 * @author Florian BRANCHEREAU
 * Classe Connexion_Rest_API qui permet de se connecter au compte google et de donner les autorisations
 */
//TODO brf by Djer Pas de underscoe dans ub=n nom de classe ! 
//TODO brf by Djer Pourrait etre abstraite ?
public class Connexion_Rest_API {
	
	private String URL;
	//TODO brf by Djer Pas de majuscule au début des varaibles !
	private String Action;
	private static URI uri;
	//TODO brf by Djer Pas de majuscule au début des varaibles !
	private String Param;
	OkHttpClient client = new OkHttpClient();
	
	/**
	 * 
	 * @param action
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public Connexion_Rest_API(String action) throws URISyntaxException, IOException
	{
		URL = "http://localhost:8080";
		this.Action = action;
		//TODO brf by Djer "Param" est toujours NULL à ce moment (on est dans le construcuteur !)
		uri = new URI(URL + action + Param);
	}
	

	/**
	 * 
	 * @param URL C'est l'url pour accéder aux différentes données
	 * @throws URISyntaxException
	 */
	protected void setURL(String URL) throws URISyntaxException {
		this.URL = URL;
		uri_Action();
	}
	
	/**
	 * 
	 * @return L'action réalisé par le client (view, add)
	 */
	protected String getAction() {
		return Action;
	}

	/**
	 * 
	 * @param Action
	 * @throws URISyntaxException
	 */
	protected void setAction(String Action) throws URISyntaxException {
		this.Action = Action;
		uri_Action();
	}

	/**
	 * 
	 * @return Le nom du parametre (nom a créer ou qui doit récupérer les infos
	 */
	protected String getParam() {
		return Param;
	}

	/**
	 * 
	 * @param Param
	 * @throws URISyntaxException
	 */
	protected void setParam(String Param) throws URISyntaxException {
		this.Param = Param;
		uri_Action();
	}
	
	/**
	 * 
	 * @return Connexion et information compte google
	 * @throws IOException
	 */
	//TODO brf by Djer Pas de majuscule au début des methodes !
	String RecupInfo() throws IOException {
    	if (uri.toURL().toString().contains("account")){
    		Desktop.getDesktop().browse(uri);
    	}
      Request request = new Request.Builder()
          .url(uri.toURL())
          .build();

      Response response = client.newCall(request).execute();
      return response.body().string();
    }
	
	/**
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	protected void OpenUri() throws URISyntaxException, IOException
	{
		try
		{
			Desktop.getDesktop().browse(uri);
		}
		catch(Exception e)
		{
			throw new IOException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @throws URISyntaxException
	 */
	//TODO brf by Djer Evite le undersocore dans un nom de méthode
	private void uri_Action() throws URISyntaxException
	{
		uri = new URI(URL + Action + Param);
	}
}