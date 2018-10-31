package fr.ynov.dap.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
/**
 * 
 * @author Florian BRANCHEREAU
 * Extends MainService
 *
 */
@Service
public class CalendarService extends GoogleService {
	
	public CalendarService() throws Exception, IOException {

	}
	
	/**
	 * 
	 * @return calendarservice
	 * @throws IOException
	 * @throws GeneralSecurityException 
	 */
	//TODO brf by Djer Pas de majuscule au début des méthodes !!!
	private Calendar BuildCalendarService(String userKey) throws IOException, GeneralSecurityException
	{
		Calendar calendarservice = new Calendar.Builder(GetHttpTransport(), GetJsonFactory(), getCredentials(userKey))
	            .setApplicationName(configuration.getApplicationName())
	            .build();
		return calendarservice;
	}
	
	/**
	 * 
	 * @return BuildCalendarService
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	//TODO brf by Djer Pas de majuscule au début des méthodes !!!
	//TODO brf by Djer Pourquoi en public ???
	//TODO brf by Djer Pourquoi créer une méthode qui fait la MEME chose que celle qu'elle appel ?
	public Calendar GetServiceCalendar(String userKey) throws IOException, GeneralSecurityException
	{
		return BuildCalendarService(userKey);
	}
	
	/**
     * 
     * @param GetServiceCalendar
     * @return List<Event>
     * @throws IOException
	 * @throws GeneralSecurityException 
     */
	//TODO brf by Djer Renvoie au plus UN seul Event, retourner Event serait plus claire.
    public List<Event> GetNextEvents(String userKey) throws IOException, GeneralSecurityException
    {
		DateTime now = new DateTime(System.currentTimeMillis());
    	return this.GetServiceCalendar(userKey).events().list("primary")
        .setMaxResults(1)
        .setTimeMin(now)
        .setOrderBy("startTime")
        .setSingleEvents(true)
        .execute().getItems();
    }
}
