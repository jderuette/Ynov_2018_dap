package fr.ynov.dap.googleController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

import fr.ynov.dap.googleService.ContactsService;

@Controller
public class ContactController {
	@Autowired
	private ContactsService contactService;
	private final Logger logger = LogManager.getLogger();

	@RequestMapping("/contactList/google/{accountName}")
	public String listcontacts(@PathVariable("accountName") String accountName, Model model)
			throws IOException, GeneralSecurityException {

		model.addAttribute("add", "List Contact pour :" + accountName);
		List<Person> connections = contactService.connections(accountName);
		List<String> listContact = new ArrayList<String>();
		if (connections != null && connections.size() > 0) {
			for (Person person : connections) {
				List<Name> names = person.getNames();
				if (names != null && names.size() > 0) {
					listContact.add(person.getNames().get(0).getDisplayName());
					model.addAttribute("ListContact", listContact);
				}
			}
		} else

		{
			logger.info("No connections found.");
			model.addAttribute("error", "No connections found.");
		}

		return "Info";
	}

	@RequestMapping("/nbContact/google/{accountName}")
	public String nbcomtact(@PathVariable("accountName") String accountName, Model model)
			throws IOException, GeneralSecurityException {
		model.addAttribute("add", "Nb Contact for :" + accountName);
		model.addAttribute("onSuccess", contactService.connections(accountName).size());
		return "Info";
	}
}
