package fr.ynov.dap.dap.Controller;

import java.io.IOException;
import java.util.List;
import javax.security.auth.callback.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.services.calendar.model.Event;
import fr.ynov.dap.dap.Service.CalendarService;
import fr.ynov.dap.dap.Service.GoogleService;

/**
 * 
 * @author Mon_PC
 * Class CalendarController
 * Manage every maps of Calendar
 */
@RestController
public class CalendarController extends GoogleService implements Callback{
	
	/**
	 * calendarService is managed by Spring on the loadConfig()
	 */
	@Autowired
	private CalendarService calendarService;
	/**
	 * Constructor of CalendarController
	 * @throws Exception
	 * @throws IOException
	 */
	public CalendarController() throws Exception, IOException
	{
	    //TODO bog by Djer Appeler super() ?
	}
	/**
	 * 
	 * @param userId
	 * UserId put in parameter
	 * @return NextEvent
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("/calendar/event/{userId}")
	public String GetFuturEvents(@PathVariable("userId") final String userId) throws IOException, Exception
	{
		List<Event> events = calendarService.GetNextEvents(userId);
		String response = "";
		if(events.isEmpty())
		{
			response += "Vous n'avez aucun prochains évenements en cours";
		}
		else
		{
			for(Event event : events)
			{
				response += "* Sujet : " + event.getSummary();
				response += "* Date début : " + event.getStart();
				response += "* Date fin : " + event.getEnd();
				//TODO bog by Djer Attention status de l'Event, pas de l'utilisateur faisant l'appel
				response += "* Etat : " + event.getStatus();
			}
		}
		//TODO bog by Djer Renoie un Objet, et laisse au client le choxi de l'affichage (texte, FR)
		return response;
	}
}