package fr.ynov.dap.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.services.google.PeopleGoogleService;
import fr.ynov.dap.services.microsoft.MicrosoftContactService;

/**
 * ContactController that uses the google and the Microsoft api.
 */
@RestController
@RequestMapping("/people")
public class ContactController {

    /**
     * Get the peopleGoogleService with Spring.
     */
    @Autowired
    private PeopleGoogleService googlePeopleService;

    /**
     * Get the MicrosoftContactService with Spring.
     */
    @Autowired
    private MicrosoftContactService microsoftContactService;

    /**
     * Get near Event from now.
     * @param userKey user key needed for authentication
     * @return near event from now
     * @throws Exception exception
     */
    @RequestMapping(value = "/number", method = RequestMethod.GET)
    public Integer getNumberOfPeople(@RequestParam("userKey") final String userKey) throws Exception {
        Integer numberOfAllGoogleContacts = googlePeopleService.getNumberOfAllContactsOfAllAccount(userKey);
        Integer numberOfAllMicrosoftContacts = microsoftContactService.getNumberOfAllContacts(userKey);
        return numberOfAllGoogleContacts + numberOfAllMicrosoftContacts;
    }

}
