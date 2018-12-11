package fr.ynov.dap.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.data.microsoft.MicrosoftEvent;
import fr.ynov.dap.service.google.CalendarService;
import fr.ynov.dap.service.microsoft.MicrosoftService;
import fr.ynov.dap.service.microsoft.OutlookService;
import fr.ynov.dap.service.microsoft.auth.TokenResponse;
import fr.ynov.dap.service.microsoft.helper.OutlookServiceBuilder;
import fr.ynov.dap.service.microsoft.helper.PagedResult;

@RestController
public class CalendarController extends BaseController {
  //TODO gut by Djer |POO| SI tu ne précise pas, cette attribut aurat la même porté que la classe (donc public).
	/**
	 *TODO gut by Djer |JavaDoc| Ce commentaire est faux ! ("Calendar" pas "People")
	 * Service permettant de communiquer avec la People API de google
	 */
	@Autowired CalendarService calendarService;

	//TODO gut by Djer |API Google| Comment obtenir LE next Event ? 
	/**
	 * @param userId
	 * @return liste des évenements à venir format JSON
	 * @throws IOException
	 */
	 @RequestMapping("/calendar/events")
	 public String getEvents(@RequestParam final String userId) 
			 throws IOException {
	     //TODO gut by Djer |Log4J| pas la peine d'ajouter "CalendarController/getEvents" cela est ajouté par defaut par Log4J (et est configurable !)
		 getLogger().debug("CalendarController/getEvents : Appel de calendar service pour recuperer les derniers evenements");
		 List<Event> events = calendarService.getEvents(userId);
		 String stringEvent = "Pas d'évènements";
		 if (events.isEmpty()) {
		     //TODO gut by Djer |POO| pas de SysOut sur un server
	         System.out.println(stringEvent);
	     } else {
	    	 stringEvent = "{events:[";
	    	 int i = 0;
	         for (Event event : events) {
	        	 DateTime eventStart = event.getStart().getDateTime();
	        	 DateTime eventStop = event.getEnd().getDateTime();
	        	 //String responseAttendees = event.getAttendees();
    			 String eventName = event.getSummary();
	             if (eventStart == null) {
	            	 eventStart = event.getStart().getDate();
	             }
	             if (eventStop == null) {
	            	 eventStop = event.getEnd().getDate();
	             }
	         	stringEvent += "{'name':'" + eventName 
	         			+ "', 'start':'" + eventStart 
	         			+ "', 'end':'" + eventStop + "'}";
	         	if(i++ != events.size() - 1) {
	         		stringEvent += ",";
	            }
	         }
	         //TODO gut by Djer |API Google| Gestion de "mon" status ?
	         stringEvent += "]}";
	     }
		 return stringEvent;
	 }
	 
	//TODO gut by Djer |SOA| Une bonne partie de ce code pourrait être dans un "MicrosoftCalendarService"
	 @RequestMapping("/outlook/events")
	  public String events(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
	    HttpSession session = request.getSession();
	    TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
	    if (tokens == null) {
	      // No tokens in session, user needs to sign in
	      redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
	      return "redirect:/index.html";
	    }
	    
	    String tenantId = (String)session.getAttribute("userTenantId");
	  	
  	    tokens = MicrosoftService.ensureTokens(tokens, tenantId);
	    
	    String email = (String)session.getAttribute("userEmail");
	    
	    OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);
	    
	    // Sort by start time in descending order
	    String sort = "Start/DateTime DESC";
	    // Only return the properties we care about
	    String properties = "Organizer,Subject,Start,End";
	    // Return at most 10 events
	    Integer maxResults = 10;
	    
	    try {
	        //TODO gut by Djer |API Microsoft| Microsodft renvoie par defaut les événnement "à partir de now" ?  
	      PagedResult<MicrosoftEvent> events = outlookService.getEvents(
	          sort, properties, maxResults)
	          .execute().body();
	      model.addAttribute("events", events.getValue());
	    } catch (IOException e) {
	      redirectAttributes.addFlashAttribute("error", e.getMessage());
	      return "redirect:/index.html";
	    }
	    
	    return "events";
	  }
}
