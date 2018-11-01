package fr.ynov.dap.dap.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

/**
 * 
 * @author Gaël BOSSER
 * Manage the redirection to the Controller
 * Extends MainService
 *
 */
//TODO bog by Djer CF remarques de brf (FLorian) car même code
@Service
public class CalendarService extends GoogleService {
	/**
	 * Constructor CalendarService
	 * @throws Exception
	 * @throws IOException
	 */
	public CalendarService() throws Exception, IOException {

	}
	/**
	 * 
	 * @param userId
	 * userId parameter put by client
	 * @return Calendar ServiceCalendar
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Calendar GetServiceCalendar(String userId) throws IOException, GeneralSecurityException
	{
		return BuildCalendarService(userId);
	}
	/**
	 * 
	 * @param userId
	 * userId parameter put by client
	 * @return Calendar service
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	private Calendar BuildCalendarService(String userId) throws IOException, GeneralSecurityException
	{
		Calendar service = new Calendar.Builder(GetHttpTransport(), GetJsonFactory(), getCredentials(userId))
	            .setApplicationName(configuration.getApplicationName())
	            .build();
		return service;
	}
	
	/**
	 * 
	 * @param userId
	 * userId parameter put by client
	 * @return List of next events
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
    public List<Event> GetNextEvents(String userId) throws IOException, GeneralSecurityException
    {
		DateTime now = new DateTime(System.currentTimeMillis());
    	return this.GetServiceCalendar(userId).events().list("primary")
        .setMaxResults(1)
        .setTimeMin(now)
        .setOrderBy("startTime")
        .setSingleEvents(true)
        .execute().getItems();
    }
}