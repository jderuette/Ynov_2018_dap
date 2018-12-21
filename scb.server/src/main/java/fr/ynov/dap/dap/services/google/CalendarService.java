package fr.ynov.dap.dap.services.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.models.CustomEvent;

@Service
public class CalendarService extends GoogleService {	
	@Autowired
	AppUserRepository repository;
	public CalendarService() {
		super();
	}
	
	/**
	 * 
	 * @param user
	 * @return Return the Calendar services that will provide info from our account
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public Calendar getService(String user) throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

        Calendar serviceCal = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.GetCredentials(user))
                .setApplicationName(config.getAppName())
                .build();
		return serviceCal;
	}
	
	/**
	 * 
	 * @param user
	 * @return a Custom Event that represent start, end , subject and title
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public CustomEvent getNextEvent(String user) throws IOException, GeneralSecurityException{
		AppUser appUser = repository.findByName(user);
		//TODO scb by Djer |POO| Vérifie si le appUser n'est pas null (cas ou le userKey n'existe pas dans la BDD)
		List<GoogleAccount> gAccounts = appUser.getAccounts();
		//TODO scb by Djer |IDE| Ton IDE te dit que ca n'est pas utilisé. Bug ? A supprimer ? Je penche sérieusement pour l'option du bug
		List<CustomEvent> customEvents = new ArrayList<CustomEvent>();
		for(int i =0; i < gAccounts.size(); i++) {
			DateTime now = new DateTime(System.currentTimeMillis());
	        Events events = getService(gAccounts.get(i).getName()).events().list("primary")
	                .setMaxResults(1)
	                .setTimeMin(now)
	                .setOrderBy("startTime")
	                .setSingleEvents(true)
	                .execute();
	        List<Event> items = events.getItems();
	        CustomEvent event = null;

	        if(items.size() != 0) {
	            //TODO scb by Djer |API Google| Attention getXXXXX().getDateTime() vaut null pour les évènnement qui dure "toutes la journée, il faut alors utiliser getXXXXX().getDate()
		        Date start = new Date(items.get(0).getStart().getDateTime().getValue());
		        Date end = new Date(items.get(0).getEnd().getDateTime().getValue());
		        //TODO scb by Djer |API Google| .getStatus() est le status de lévènnement, pas "MON" status
		        event = new CustomEvent(start, end, items.get(0).getSummary(),items.get(0).getStatus());
		        //TODO scb by Djer |POO| Un parfait exemple de "il fallait PAS faire de multiple return, après on se piège". Tu ne récupèras que l'event du premier Gogole account qui a un event, car ton return "casse" ta boucle for
		        return event;
	        }
		}
		
		return null;
	}
}
