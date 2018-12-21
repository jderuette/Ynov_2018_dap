 package fr.ynov.dap.dap.services.microsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.OutlookAccount;
import fr.ynov.dap.dap.models.CustomEvent;
import fr.ynov.dap.dap.models.Event;
import fr.ynov.dap.dap.models.PagedResult;

@Service
public class OutlookCalendarService {
  //TODO scb by Djer |POO| Pourquoi public ?
    @Autowired
    AppUserRepository repository;
    
  //TODO scb by Djer |POO| Le nom des méthodes ne doivent pas commencer par une majuscule
    public CustomEvent GetNextEvent(String userid) {
		AppUser appUser = repository.findByName(userid);
		List<OutlookAccount> otAccounts = appUser.getOutlookAccounts();
		CustomEvent customEvent;
		List<Event> foundEvent = new ArrayList<Event>();

		for(int i=0; i < otAccounts.size(); i++) {
		    OutlookService outlookService = OutlookServiceFactory.getOutlookService(otAccounts.get(i).getIdToken().getAccessToken());
		    try {
		    	 // Sort by start time in descending order
			    String sort = "start/dateTime ASC";
			    // Only return the properties we care about
			    String properties = "organizer,subject,start,end";
			    // Return at most 10 events
			    //TODO scb by Djer |API Microsoft| Tu pourrais n'en récupérer qu'un seul par compte
			    Integer maxResults = 10;
			    //TODO scb by Djer |API Microsoft| Microsoft renvoie par defaut les évènnements à venir ? (ajoute un filtre)
			    PagedResult<Event> events = outlookService.getEvents(sort, properties, maxResults).execute().body();
			    for(int y = 0; y< events.getValue().length; y++) {
		    		foundEvent.add(events.getValue()[y]);
			    }
		    }catch(IOException e) {
		      //TODO scb by Djer |Log4J| Une petite log ?
		        //TODO scb by Djer |Gestion Exception| Attention tu "ettoufes" cette exception (en donnant l'impression à l'apellant qu'il n'y a pas de NextEvent). Deplus tu arretes dès qu'il y a une erreur sur un des comptes
		    	return null;
		    }
		}
		//TODO scb by Djer |POO| Il est possible qu'il n'y ai pas d'évènnements et tu auras un "arrayOutOfBoundException"
		//TODO scb by Djer |API Microsoft| Les évéènnements ne peuvent être que "confirmed" dans les agenda Microsoft ?
		customEvent = new CustomEvent(foundEvent.get(0).getStart().getDateTime(), foundEvent.get(0).getEnd().getDateTime(), foundEvent.get(0).getSubject(), "confirmed");
		Date now = new Date();
		for(int i = 1; i< foundEvent.size(); i++) {
			Event e = foundEvent.get(i);
			//TODO scb by Djer |POO| Tu n'es pas certains que le premier de la liste soit le plus proche. Peut-être que le "premier du deuxième comtpe" est plus proche (il sera alors peut-être en 11ème position s'il y avait 10 évènnements à venir dans le premier compte)
			if(e.getStart().getDateTime().after(now)) {
			    //TODO scb by Djer |POO| Evite les multiples returns dans une même méthode. Alimente customEvent et "break" si tu souhaite intérompre la boucle
				return new CustomEvent(e.getStart().getDateTime(), e.getEnd().getDateTime(), e.getSubject(), "confirmed");
			}
		}
	    return customEvent;

    }
}
