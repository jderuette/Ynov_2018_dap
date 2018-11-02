package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.websocket.server.PathParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.dap.service.GmailService;

/**
 * The Class GmailController.
 */
@RestController
@RequestMapping("/getEmails")
public class GmailController {

	/**
	 * Gets the label.
	 *
	 * @param user the user
	 * @return the label
	 */
	@RequestMapping("/getLabel/userKey={user}")
	public @ResponseBody String getLabel(@PathParam("user") String user) {
		String label = null;
		try {
		  //TODO brs by Djer IOC ?
			label = GmailService.getLabelInfo(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return label;

	}
}
