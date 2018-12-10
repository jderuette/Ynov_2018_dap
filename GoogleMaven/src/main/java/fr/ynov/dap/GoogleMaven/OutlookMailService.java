package fr.ynov.dap.GoogleMaven;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import fr.ynov.dap.GoogleMaven.auth.AuthHelper;
import fr.ynov.dap.GoogleMaven.auth.TokenResponse;
import fr.ynov.dap.GoogleMaven.service.Message;
import fr.ynov.dap.GoogleMaven.service.OutlookServiceBuilder;
import fr.ynov.dap.GoogleMaven.service.PagedResult;
/**
 * 
 * @param user
 * @return mail service with outlook api
 * @throws IOException
 * @throws GeneralSecurityException
 */

@Service
public class OutlookMailService {
	private int i = 0;
	private  final static Logger logger = LogManager.getLogger();
	public Message[] mail(ModelMap model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
		String tenantId = (String)session.getAttribute("userTenantId");

		tokens = AuthHelper.ensureTokens(tokens, tenantId);

		String email = (String)session.getAttribute("userEmail");

		fr.ynov.dap.GoogleMaven.service.OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		 
		String folder = "inbox";
 		String sort = "receivedDateTime DESC";
 		String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
 		//TODO elj by Djer |IDE| Ton IDE te dit que ca n'est pas utilsié. Bug ? A supprimer ?  
		String propertiesforunread = "isRead";
 		Integer maxResults = 1000;
		PagedResult<Message> messages = null;
		try {
			 messages = outlookService.getMessages(
					folder, sort, properties, maxResults)
					.execute().body();

			model.addAttribute("messages", messages.getValue());
			
           PagedResult<Message> Unreadmails = outlookService.getMessages(
					folder, sort, properties, maxResults)
					.execute().body();
			
		    Message[] msgs = Unreadmails.getValue();
		    i  = 0;
		    for (Message msg : msgs) {
		    	if (msg.getIsRead() == false)
		        i++;
		    }

			model.addAttribute("nbmailsoutlook", i);
		} catch (IOException e) {
			logger.warn("Une erreur a été détecter dans le service mail outlook avec comme details : "+e.getMessage());

		}

		return messages.getValue();
	}
}
