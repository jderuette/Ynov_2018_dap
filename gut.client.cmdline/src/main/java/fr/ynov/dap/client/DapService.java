package fr.ynov.dap.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.*;


public class DapService {

	String userId;
	
	DapService(String _userId){
		userId = _userId;
	}
	
	/**
	 * Gère les appels à l'api rest
	 * @param url
	 * @return Réponse du serveur
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public InputStream callApi(String url) throws MalformedURLException, IOException {
        // appel 
        
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
     
        
        String param1 = userId;
        // ...

        String query = String.format("userId=%s", 
         URLEncoder.encode(param1, charset));
    
        URLConnection connection = new URL(url + "?" + query).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();

        return response;
	}
		
	/**
	 * Récupération du nombre de mails non lus
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void getUnreadMailsCount() throws MalformedURLException, IOException {
		String url = "http://localhost:8080/gmail/countUnread";
		
		//TODO gut by Djer |POO| une grosse partie de ce code est commun avec tes autres méthodes. Soit ajouter dnas "callAPI" soit une méthode pour "extraire la réponse" serait approprié
		
		 try (InputStream response = callApi(url)) {
			 
			 BufferedReader bR = new BufferedReader(  new InputStreamReader(response));
			 String line = "";

			 StringBuilder responseStrBuilder = new StringBuilder();
			 while((line =  bR.readLine()) != null){

			     responseStrBuilder.append(line);
			 }
			 response.close();

			 JSONObject result= new JSONObject(responseStrBuilder.toString());   
		
            System.out.println("Vous avez " + result.getString("mailCount") + " mails non lus");
        }
	}
	
	/**
	 * Récupération des labels de Gmail
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void getGmailLabels() throws MalformedURLException, IOException {
		String url = "http://localhost:8080/gmail/labels";
		
		
		try (InputStream response = callApi(url)) {
			 
			 BufferedReader bR = new BufferedReader(  new InputStreamReader(response));
			 String line = "";

			 StringBuilder responseStrBuilder = new StringBuilder();
			 while((line =  bR.readLine()) != null){

			     responseStrBuilder.append(line);
			 }
			 response.close();

			 JSONObject result= new JSONObject(responseStrBuilder.toString());   
			 JSONArray arr = result.getJSONArray("labels");
			 System.out.println("Labels : \n \n \n");
			 for (int i = 0; i < arr.length(); i++)
			 {
			     String name = arr.getJSONObject(i).getString("label");	
			     
			     System.out.println("Libellé: " + name + "\n");
			 }		 
			            
       }
	}
	
	/**
	 * Récupération des évènements de l'utilisateur
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void getEvents() throws MalformedURLException, IOException {
		String url = "http://localhost:8080/calendar/events";
		
		
		try (InputStream response = callApi(url)) {
			 
			 BufferedReader bR = new BufferedReader(  new InputStreamReader(response));
			 String line = "";

			 StringBuilder responseStrBuilder = new StringBuilder();
			 while((line =  bR.readLine()) != null){

			     responseStrBuilder.append(line);
			 }
			 response.close();

			 JSONObject result= new JSONObject(responseStrBuilder.toString());   
			 JSONArray arr = result.getJSONArray("events");
			 System.out.println("\n \n Evènements: \n");
			 for (int i = 0; i < arr.length(); i++)
			 {
			     String name = arr.getJSONObject(i).getString("name");	
			     String startDay = arr.getJSONObject(i).getString("start");	
			     String endDay = arr.getJSONObject(i).getString("end");	
			     
			     System.out.println("Nom : " + name + "\n  Début: " + startDay + "\n  Fin: " + endDay + "\n \n");
			 }
			 
      }
	}
	
	/**
	 * Récupération du nombre de contacts
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void getContactsCount() throws MalformedURLException, IOException {
		String url = "http://localhost:8080/contact/getCount";
		
		
		try (InputStream response = callApi(url)) {
			 
			 BufferedReader bR = new BufferedReader(  new InputStreamReader(response));
			 String line = "";

			 StringBuilder responseStrBuilder = new StringBuilder();
			 while((line =  bR.readLine()) != null){

			     responseStrBuilder.append(line);
			 }
			 response.close();

			 JSONObject result= new JSONObject(responseStrBuilder.toString());   
				
	            System.out.println("Vous avez " + result.getString("contactCount") + " contacts");
      }
	}
	
}
