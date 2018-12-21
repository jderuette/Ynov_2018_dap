package fr.ynov.dap.controllers.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.services.google.GmailService;
import fr.ynov.dap.services.microsoft.OutlookService;
import fr.ynov.dap.utils.ExtendsUtils;
import fr.ynov.dap.utils.JSONResponse;

import java.io.IOException;

/**
 * The Class MailController.
 */
@RestController
@RequestMapping("/mail")
public class MailController extends ExtendsUtils {

	/** The google service. */
	@Autowired
	private GmailService googleService;

	/** The microsoft service. */
	@Autowired
	private OutlookService microsoftService;

	/**
	 * Index.
	 *
	 * @param user the user
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/{user}")
	//TODO thb by Djer |Spring| Par defaut les méthodes mappées dans un **Rest**Controller renvoie le body. C'est redondant de le préciser
	public @ResponseBody String index(@PathVariable String user) throws IOException {
		Integer nbEmails = 0;
		nbEmails = googleService.getAllEmails(user);
		return JSONResponse.responseString("nb_mails", nbEmails.toString());
	}

	/**
	 * Gets the emails.
	 *
	 * @param userKey the user key
	 * @return the emails
	 */
	@RequestMapping("/unread")
	//TODO thb by Djer |Spring| Par defaut les méthodes mappées dans un **Rest**Controller renvoie le body. C'est redondant de le préciser
	public @ResponseBody String getEmails(@RequestParam(value = "userKey", required = true) String userKey) {
		Integer nbEmails = 0;
		nbEmails += googleService.getAllEmails(userKey);
		nbEmails += microsoftService.getUnreadEmails(userKey);
		return JSONResponse.responseString("unread_emails", nbEmails.toString());
	}
}