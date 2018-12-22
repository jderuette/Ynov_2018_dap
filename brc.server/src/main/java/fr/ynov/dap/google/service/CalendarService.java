package fr.ynov.dap.google.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.GoogleAccount;
import fr.ynov.dap.models.CalendarResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * The Class CalendarService.
 */
@Service
public class CalendarService extends GoogleService {

	/**
	 * Instantiates a new calendar service.
	 */
	private CalendarService(){
		super();
	}
	
	/** The Constant logger. */
	//TODO brc by Djer |POO| Devrait être écris en Majuscule (car static et final)
	//TODO brc by Djer |Audit Code| static devrait être avant final (PMD/Checkstyle te préviennent de cette inversion)
	private final static Logger logger = LogManager.getLogger(CalendarService.class);
	
    /**
     * Result calendar.
     *
     * @param accountName the account name
     * @return the calendar response
     * @throws Exception the exception
     */
    private CalendarResponse resultCalendar(final String accountName) throws Exception {
    	
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, jsonFactory, getCredentials(HTTP_TRANSPORT, cfg.getCredentialsFilePath(),accountName))
                .setApplicationName(cfg.getApplicationName())
                .build();

        // List the next event from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            logger.info("No upcoming events found. for user : " + accountName);
            return new CalendarResponse("No upcoming events found. for user : " + accountName);
        } else {
            //TODO brc by Djer |log4J| Attention du Log en INFO et en Debug, suivant la configuratio nde Log4J certains messages ne seront pas visibles
            logger.info("Upcoming events");
            
            Event nextEvent = items.get(0);
            
            //TODO brc by Djer |API Google| attention .getXXXXX().getDateTime() est null pour les évènnement qui durent toute la journée, pour cela il faut utiliser getStart().getDate()
            Date start = new Date(nextEvent.getStart().getDateTime().getValue());
            Date end =  new Date(nextEvent.getEnd().getDateTime().getValue());
	        String subject = nextEvent.getSummary();
	        String status = nextEvent.getStatus();
	        String organizer = nextEvent.getOrganizer().getDisplayName();
	        
	        //TODO brc by Djer |Log4J| Evite de logger une "même informations" en plusieurs morceau, il peut y avoir d'autre logs qui se place entre ce qui rendra peu lisible (surtout si ces logs proviennent de la même méthode, mais pour un autre utilisateur). Contextualise systématiquement tes messages
	        logger.debug("start : " + start);
	        logger.debug("end : " + end);
	        logger.debug("subject : " + subject);
	        logger.debug("status : " + status);
	        
	        //TODO brc by Djer |API Google| gestion de "MON" status ?
	        return new CalendarResponse(organizer,subject,start,end,status);   
        }
    }
    
    /**
     * Gets the google next event from account.
     *
     * @param appUser the app user
     * @return the google next event from account
     * @throws Exception the exception
     */
    public CalendarResponse getGoogleNextEventFromAccount(final AppUser appUser) throws Exception {
    	
    	if (appUser.getGoogleAccounts().size() == 0) {
        	logger.info("no google account found for userkey : " + appUser.getUserKey());
            return null;
        }
    	
    	List<CalendarResponse> events = new ArrayList<CalendarResponse>();
		
    	//TODO brc by Djer |POO| Pas très utile de le tester ici, si appUser etait null, tu aurais déja eut un NPE avant 
		if(appUser != null) {
			for (GoogleAccount account : appUser.getGoogleAccounts()) {
				String accountName = account.getName();

				if(accountName != null) {
					CalendarResponse response = this.resultCalendar(accountName);
					events.add(response);
				}
			}
		}
		
		CalendarResponse googleNextEvent = null;
		//TODO brc by Djer |POO| Attention ici tu vérifie que 1-il y a un élément "0" ET 2-+ qu'il n'est aps null. S'il n'y a pas d'élement tu auras un "ArrayOutOfBoundExcemption". Tu peux tester si size() != 0
		if(events.get(0) != null) {
			googleNextEvent = events.get(0);
			for(int i=0; i<events.size(); i++) {
				if(events.size()>1 && events.get(i).getStart().before(googleNextEvent.getStart())) {	
					googleNextEvent = events.get(i);
				}	
			}
		}
		//TODO brc by Djer |API Google| Gestion de "MON" status sur l'éevent ?
		return googleNextEvent;
    }
}