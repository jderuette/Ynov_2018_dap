package fr.ynov.dap.dap.controllers.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.services.google.GmailService;

/**
 * The Class GmailController.
 */
@RestController
@RequestMapping("/emails")
public class GmailController {

	@Autowired
	private GmailService gmailService;

	protected Logger LOG = LogManager.getLogger(GmailController.class);

	/**
	 * Gets the nbr unread mail.
	 *
	 * @param userId the user id
	 * @return the nbr unread mail
	 * @throws JSONException
	 */
	//TODO mot by Djer |Spring| "@ResponseBody" n'est pas utile, par defaut les méthodes "mappées" d'un **Rest**Controller produisent un Body
    //TODO mot by Djer |Spring| le "required = true" est la valeur par defaut, tu n'es pas obligé de le préciser
	//TODO mot by Djer |POO| dnas le contact et Calendar Controller, la méthode gère les exception. Pourquoi les laisse-tu remonter pour cette méthode ? 
	@RequestMapping("/nbrUnreadMailGoogle")
	public @ResponseBody String getNbrUnreadMail(@RequestParam(value = "userKey", required = true) String userKey)
			throws IOException, GeneralSecurityException, JSONException {

		Integer nbrEmailUnread = 0;
		String response = "errorOccurs";

		nbrEmailUnread = gmailService.nbrEmailUnread(userKey);
		JSONObject json = new JSONObject();
		json.put("nbmailunread", nbrEmailUnread);
		response = json.toString();
		return response;
	}
}
