package fr.ynov.dap.dap.service.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;

//TODO brs by Djer |IDE| Formate ton code !
/**
 * The Class CalendarService.
 */
@Service
public class CalendarService extends GoogleServices {

	public CalendarService() throws IOException, GeneralSecurityException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired 
	public AppUserRepository repo;
	
	 public Event getEvent(String user) throws IOException, GeneralSecurityException {
	        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	        Calendar calendarService = new Calendar.Builder(HTTP_TRANSPORT, super.getJSON_FACTORY(), getCredentials(user))
	                .setApplicationName(getConfiguration().getApplicationName())
	                .build();

	        DateTime now = new DateTime(System.currentTimeMillis());
	        Events events = calendarService.events().list("primary")
	                .setMaxResults(1)
	                .setTimeMin(now) 
	                .setOrderBy("startTime")
	                .setSingleEvents(true)
	                .execute();
	        
	        Integer lastEvent = events.getItems().size() - 1;
	        if(lastEvent == 0) {
	            //TODO brs by Djer |Log4J| Pas vraiment une Erreur. Contextualise tes messages (" for accountName :" + user)
	        	LOG.error("No event");
	        }
		
			Event event = events.getItems().get(lastEvent);

	        return event;
	    }
	    
	 //TODO brs by Djer |Javadoc| Quel diffétrence avec la "getEvent(String)"
	    public Event getNextEvent(String user) {
	    	AppUser userDb = repo.findByUserkey(user);
	    	
	    	if(user == null) {
	    	    //TODO brs by Djer |Log4J| Contextualise tes messages
				LOG.info("no user");
				return null;
			}
	    	
	    	//TODO brs by Djer |POO| Nom de variable pas très claire
	    	Event event1 =  null;
	    	Integer count = 0;
	    	for (GoogleAccount googleAccount: userDb.getGoogleAccounts()) {
	    	    //TODO brs by Djer |POO| Evite d'apeller plusieurs fois ta méthode "getEvent" avec même paramètre, appel une fois au début de la boucle puis utuilise le résultat
	    	    
				try {
					if (count > 0) {
						Long longDate1 = event1.getStart().getDateTime().getValue();
						Date date1 = new Date(longDate1);
						
						Long longDate2 = getEvent(googleAccount.getAccountName()).getStart().getDateTime().getValue();
						Date date2 = new Date(longDate2);
						
						if(date1.compareTo(date2) > 0) {
							event1 = getEvent(googleAccount.getAccountName());
						}
					}else {
						event1 = getEvent(googleAccount.getAccountName());
					}
					} catch (IOException e) {
					    //TODO brs by Djer |Log4J| Log la cause de l'exception. En général on construit le message dans le premièr argument, puis on utilise le deuxième pour indiquer la casue (l'exception que l'on a"catché))
						LOG.error("Google account : ",googleAccount);
						//TODO brs buy Djer |Log4J| Evite de logger l'erreur indépendament du messages. D'autres messages peuvent se positionner entre message et log rendant le fichier de log incompréhenssible
						LOG.error(e);
						//TODO brs by Djer |Log4J| "e.printStackTrace()" affiche dictement dans la console, il faut éviter de l'utiliser, une Log (avec la cause) est bien plus claire
						e.printStackTrace();
					} catch (GeneralSecurityException e) {
						LOG.error("Google account : ",googleAccount);
						LOG.error(e);
						e.printStackTrace();
					}
	    	}
	    	
	    	return event1;
	    }
}
