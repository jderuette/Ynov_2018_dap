package fr.ynov.dap.GoogleMaven;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import fr.ynov.dap.GoogleMaven.auth.TokenResponse;
import fr.ynov.dap.GoogleMaven.service.Event;
import fr.ynov.dap.GoogleMaven.service.OutlookService;
import fr.ynov.dap.GoogleMaven.service.OutlookServiceBuilder;
import fr.ynov.dap.GoogleMaven.service.PagedResult;

/**
 * 
 * @param user
 * @return Get Events with outlook api
 * @throws IOException
 * @throws GeneralSecurityException
 */

@Service
public class OutlookEvents {
	
	private  final static Logger logger = LogManager.getLogger();
	
	public Event[] events(ModelMap model, HttpServletRequest request) throws IOException {
		PagedResult<Event> events = null;
		try {
			HttpSession session = request.getSession();
			TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
			
			Date now = new Date();

			
			String email = (String)session.getAttribute("userEmail");
			
			OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);
			
			// Sort by start time in descending order
			String sort = "start/DateTime DESC";
			// Only return the properties we care about
			String properties = "organizer,subject,start,end";
			// Return at most 10 events
			Integer maxResults = 10;
			events = outlookService.getEvents(
					sort, properties, maxResults)
					.execute().body();
		
			
			
		}
		catch (Exception e){
			logger.warn("Une erreur a été détecter dans le service evenements outlook avec comme details : "+e.getMessage());

		}
		return events.getValue();
	}
}
