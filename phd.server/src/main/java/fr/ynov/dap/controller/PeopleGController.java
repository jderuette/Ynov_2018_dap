package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.people.v1.PeopleService;

import fr.ynov.dap.service.GmailService;
import fr.ynov.dap.service.PeopleGService;
/**
 * 
 * @author Dom
 *This class return the number of contact with a string param "userId" in format String
 */
@RestController
public class PeopleGController {
	/**
	 * Return the unique instance of PeopleService with annotation Autowired
	 */
	@Autowired
	PeopleGService peopleService;
	
	/**
	 * This function return the number of contact unread with string param userId according to the annotated route
	 * @param userId
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
    @RequestMapping("/getPeople")
    public String getNbContact(@RequestParam("userId") final String userId) throws IOException, GeneralSecurityException {
    	return "Nb contact : " + peopleService.nbContact(userId);
    }
    	

}
