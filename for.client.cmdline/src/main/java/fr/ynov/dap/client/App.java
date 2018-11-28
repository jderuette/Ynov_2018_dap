package fr.ynov.dap.client;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class App {
/**
 * fonction main du client
 * @param args
 * @throws IOException
 * @throws URISyntaxException 
 */
  public static void main(String[] args) throws IOException, URISyntaxException {
    if(args.length > 0) {
    	if(args[0].equals("addAccount") && args[1] != null)
    	{
            URL addAccountURI = new URL(link.ADD_ACCOUNT + args[1]);
            makeRequest(addAccountURI);
    	}
    	else if(args[0].equals("addGoogleAccount") && args[1] != null) {
        System.out.println(args[1]);
        URI addAccountURI = URI.create(link.ADD_GOOGLE_ACCOUNT + args[1]);
        Desktop.getDesktop().browse(addAccountURI);
      }
      else if(args[0].equals("getLabels") && args[1] != null) {
        URL labelsURL = new URL(link.LABEL_NAME + args[1]);
        makeRequest(labelsURL);
      }
      else if(args[0].equals("EmailCount") && args[1] != null) {
        URL emailCountURL = new URL(link.NB_UNREAD_MAIL + args[1]);
        makeRequest(emailCountURL);
      }
      else if(args[0].equals("nbContact") && args[1] != null) {
        URL nbContactURL = new URL(link.NB_CONTACT + args[1]);
        makeRequest(nbContactURL);
      }
      else if(args[0].equals("nextEvent") && args[1] != null) {
        URL nextEventURL = new URL(link.NEXT_EVENT + args[1]);
        makeRequest(nextEventURL);
      }
      else if(args[0].equals("getAll") && args[1] != null) {
        URL labelsURL = new URL(link.LABEL_NAME + args[1]);
        System.out.println("Nom des labels: ");
        makeRequest(labelsURL);
        URL emailCountURL = new URL(link.NB_UNREAD_MAIL + args[1]);
        System.out.println("Nombre de mails non-lus: ");
        makeRequest(emailCountURL);
        URL nbContactURL = new URL(link.NB_CONTACT + args[1]);
        System.out.println("Nombre total de contacts: ");
        makeRequest(nbContactURL);
        URL nextEventURL = new URL(link.NEXT_EVENT + args[1]);
        System.out.println("Prochain evennement de l'agenda: ");
        makeRequest(nextEventURL);
      }
      else if(args[0].equals("loginMicrosoft") && args[1] != null)
      {
    	  URI loginMicrosoftURL = new URI(link.MICROSOFT_LOGIN + args[1]);
    	  Desktop.getDesktop().browse(loginMicrosoftURL);
      }
      else
      {
        System.err.println("Commande invalide.");
      }
    }
    else
    {
      System.err.println("No arguments");
    }
  }
  
  //Fonction pour effectuer une requete sur un url spécifique et lire la réponse
  private static void makeRequest(URL url) throws IOException
  {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    try {
      InputStream in = new BufferedInputStream(urlConnection.getInputStream());

      BufferedReader rd = new BufferedReader(new InputStreamReader(in));
      String line;
      while ((line = rd.readLine()) != null) {
        System.out.println(line);
      }
    } finally {
      urlConnection.disconnect();
    }
  }
}