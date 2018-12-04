package fr.ynov.dap.client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ynov.dap.model.ContactModel;
import fr.ynov.dap.model.EmptyData;
import fr.ynov.dap.model.EventModel;
import fr.ynov.dap.model.MailModel;


/**
 * Hello world!
 *
 */
public class App 
{
	/**
	 * 
	 * @param args passed to the jar must be Add or View for the first and the UserID For the second must contain only 2 elements
	 * @throws IOException
	 * @throws URISyntaxException
	 */
    public static void main( String[] args ) throws IOException, URISyntaxException
    {
    	ObjectMapper mapper = new ObjectMapper();
    	
    	if(args.length  < 2) {
    		printUsage();
    		return;
    	}
        switch(args[0]) {
        //FIXME bof by Djer |API Microsoft| Ajout de compte Microsoft ?
        //FIXEME vof by Djer |Rest API| Ajout d'utilisateur ? 
        case "Add":
        	Desktop.getDesktop().browse(new URI(Config.URL_BASE + Config.ADD_ACCOUNT + args[1]));
        	break;
        case "View": {
        	String unreadJSON = HttpHelper.sendGetRequest(Config.NB_UNREAD + args[1]);
        	MailModel mail = mapper.readValue(unreadJSON, MailModel.class);
        	System.out.println(mail);
        	String nbContactJSON = HttpHelper.sendGetRequest(Config.NB_CONTACT + args[1]);
        	ContactModel contact = mapper.readValue(nbContactJSON, ContactModel.class);
        	System.out.println(contact);
        	String eventJSON = HttpHelper.sendGetRequest(Config.UPCOMMING_EVENT + args[1]);
        	try {
        		EventModel event = mapper.readValue(eventJSON, EventModel.class);
        		System.out.println(event);
        	}catch(Exception e) {
        		EmptyData empty = mapper.readValue(eventJSON, EmptyData.class);
        		System.out.println(empty);
        	}
        	break;
        }
        default:
        	App.printUsage();
        	return;
        }
    }
    
    
    public static void printUsage() {
    	System.out.println("Usage: executable-jar [Command]{Add,View} [UserID]" );
    }
}
