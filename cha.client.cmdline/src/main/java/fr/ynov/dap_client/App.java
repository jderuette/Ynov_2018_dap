package fr.ynov.dap_client;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The Class App.
 */
public class App
{
  
  /** The localhost. */
  public static String localhost = "http://localhost:8080";
  
  /** The route. */
  public static String route;

  /**
   * The main method.
   *
   * @param args the arguments
   */
  //TODO cha by Djer |POO| Il manque aussi la gestion des exceptions, mais là je ne vais pas le corriger
  public static void main(String[] args)
  {
      if(args[0] == "account"){
    	  
    	if(args[1] == "add") {
    		route = localhost + "/add/account/add?userKey=" + args[2];
    		//TODO cha by Djer |POO| Tu avais un acollade fermante en trop ici ....
      	}else if(args[1] == "google"){
          route = localhost + "/account/google/add/" + args[2] + "?userKey=" + args[3];
        }else if(args[1] == "microsoft") {
          route = localhost + "/account/microsoft/add/" + args[2] + "?userKey=" + args[3];
        }
        URI uri = new URI(route);
        Desktop.getDesktop().browse(uri);
      }else {
        if(args[0] == "mail"){
          route = localhost + "/mail/nbUnread?userKey=" + args[1];
          URL path = new URL(route);
          System.out.println(request(path));
        }else if(args[0] == "calendar"){
          route = localhost + "/calendar/nextEvent?userKey=" + args[1];
          URL path = new URL(route);
          System.out.println(request(path));
        }else if(args[0] == "people"){
          route = localhost + "/people/nbPeople?userKey=" + args[1];
          URL path = new URL(route);
          System.out.println(request(path));
        }
      //TODO cha by Djer |POO| Et il manquait une acollade fermante ici
      }
  }

  /**
	 * Request.
	 *
	 * @param path the path
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String request(URL path) throws IOException {
		HttpURLConnection con = (HttpURLConnection) path.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();
		return response.toString();
	}
}
