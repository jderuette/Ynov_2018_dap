package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.websocket.server.PathParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.service.ContactService;

/**
 * The Class ContactController.
 */
@RestController
@RequestMapping("/getContact")
public class ContactController {

	/**
	 * Gets the nb contact.
	 *
	 * @param user the user
	 * @return the nb contact
	 */
	@RequestMapping("GetNbCOntact/userKey={user}")
	public @ResponseBody Integer getNbContact(@PathParam("user") String user) {

		Integer people = 0;

		try {
		  //TODO brs by Djer IOC ?
			people = ContactService.getNbContact(user);

			//TODO brs by Djer 3 fois le même return dans 3 blocks !!? Et un seul return à la fin des 3 blocks ?
			return people;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return people;

		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return people;

		}

	}

}
