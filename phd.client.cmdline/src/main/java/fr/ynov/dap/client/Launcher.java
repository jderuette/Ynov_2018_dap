package fr.ynov.dap.client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ynov.dap.client.model.Email;
import fr.ynov.dap.client.model.Event;
import fr.ynov.dap.client.service.RequestHttpService;


/**
 * Launch class
 * @author Dom
 *
 */
public class Launcher 
{
	/**
	 * requestHttpService is a variable containing the RequestHttpService;
	 */
	private static RequestHttpService requestHttpService = new RequestHttpService();
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws URISyntaxException
	 */
    public static void main(final String[] args) throws IOException, URISyntaxException
    {
        if (args[0].equals("view")) {
        	if (args[1] != null) {
        		if (args[1].equals("email")){
        			String userId = args[2];
            		getRequestEmailUnread("http://localhost:8080/getEmailUnread?userId="+userId);
        		}
        	}
        	
        	if (args[1] != null) {
        		if (args[1].equals("event")){
        			String userId = args[2];
        			getRequestNextEvent("http://localhost:8080/getEventUncomming?userId="+userId);
        		}
        	}
        	
        	if (args[1] != null) {
        		if (args[1].equals( "people")){
        			String userId = args[2];
        			getRequestNbPeople("http://localhost:8080/getPeople?userId="+userId);
        		}
        	}
        }
        
        if (args[0].equals("add")) {
        	if (args[1] != null) {
        		String userId = args[1];
        		//TODO phd by Djer Est-ce vraiment utile d'afficher le HTML de la page Google sur le client ?
        		getRequestAddUser("http://localhost:8080/account/add/"+userId);
        		URI uri = new URI("http://localhost:8080/account/add/"+userId);
        		Desktop.getDesktop().browse(uri);
        	}
        }
    }

/**
 * 
 * @param url
 * @throws IOException
 */
    public static void getRequestAddUser(final String url) throws IOException {

    	requestHttpService.executeServiceGet(new IApiResponse() {
			
			public void onSuccess(String string) {
				System.out.println(string);
			}
			
			public void onError(IOException e) {
				
			}
		}, url);
    }
    /**
     * Write in console the nextEvent according to a string param
     * @param url
     * @throws IOException
     */
    public static void getRequestNextEvent(String url) throws IOException {

    	requestHttpService.executeServiceGet(new IApiResponse() {
			
			public void onSuccess(String string){
				
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					Event event = objectMapper.readValue(string, Event.class);
					System.out.println("The next event is : " + event.getEventUncomming() 
							+ " the event start on :" + event.getEventBegin() + " and finish at : " 
							+ event.getEventFinish() + " and you have : " + event.getEventStatus() 
							+ " this event");
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}


			}
			
			public void onError(IOException e) {
				
			}
		}, url);
    }
    /**
     * Write in the console the number of people according to a string param
     * @param url
     * @throws IOException
     */
    public static void getRequestNbPeople(String url) throws IOException {

    	requestHttpService.executeServiceGet(new IApiResponse() {
			
			public void onSuccess(final String string) {
				System.out.println(string);
			}
			
			public void onError(final IOException e) {
				
			}
		}, url);
    }
    /**
     * Write in a console the number of email unread according to a string param
     * @param url
     * @throws IOException
     */
    public static void getRequestEmailUnread(final String url) throws IOException {

    	requestHttpService.executeServiceGet(new IApiResponse() {
			
			public void onSuccess(final String string) {
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					Email email = objectMapper.readValue(string, Email.class);
					System.out.println("You have " + email.getNbMessageUnread() +" email unread");
				} catch (JsonParseException e) {

					e.printStackTrace();
				} catch (JsonMappingException e) {
					
					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			
			public void onError(final IOException e) {
				
			}
		}, url);
    }

}
