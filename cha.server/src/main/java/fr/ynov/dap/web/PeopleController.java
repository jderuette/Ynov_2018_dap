package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.exception.UserException;
import fr.ynov.dap.google.PeopleAPIService;
import fr.ynov.dap.microsoft.OutlookService;

@RestController
@RequestMapping(value="/people")
public class PeopleController extends BaseController {
	
	/** The gmail service. */
	@Autowired
	private PeopleAPIService peopleService;

    /**
     * Instance of Outlook service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private OutlookService outlookService;
    

    @Override
    protected final String getClassName() {
        return PeopleController.class.getName();
    }

    /**
     * Get number of contacts from every account (ms, google, ...).
     * @param userId User id
     * @return NumberContactOutDto instance
     * @throws GeneralSecurityException Security exception
     * @throws IOException Exception
     * @throws UserNotFoundException No user found for this user id
     * @throws NoGoogleAccountException No google account found for this user
     * @throws NoMicrosoftAccountException No microsoft found fot this user
     */
    @RequestMapping("/nbPeople/{userId}")
    public final Integer getNumberOfPeople(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserException {

        AppUser user = getUserById(userId);

        Integer googleNbPeople = peopleService.getTotalPeopleAllAccount(user);

        Integer microsoftNbPeople = outlookService.getNumberOfPeoples(user);
        
        Integer totalPeople = googleNbPeople + microsoftNbPeople;

        return totalPeople;

    }

    /**
     * Endpoint to get the user's number of contact from every Google account.
     * @param userId User's Id
     * @return Number of contact for user linked by userId. JSON Formatted.
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws UserNotFoundException Thrown when the user is not found.
     * @throws NoGoogleAccountException Thrown when the user haven't any google account.
     */
    @RequestMapping("/google/nbPeople/{userId}")
    public final Integer getGoogleNumberOfPeople(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserException {

        AppUser user = getUserById(userId);

        Integer nbPeople = peopleService.getTotalPeopleAllAccount(user);

        return nbPeople;

    }

    /**
     * Endpoint to get the user's number of contact from every Microsoft accounts.
     * @param userId User's Id
     * @return NumberContactOutDto instance
     * @throws UserNotFoundException No user found
     * @throws NoMicrosoftAccountException No microsoft account linked with this user
     * @throws NoNextEventException no next event for current user on microsoft
     * @throws IOException exception
     */
    @RequestMapping("/microsoft/nbPeople/{userId}")
    public Integer getMicrosoftNumberOfPeople(@PathVariable("userId") final String userId)
            throws UserException, IOException {

        AppUser user = getUserById(userId);

        Integer nbOfPeople = outlookService.getNumberOfPeoples(user);

        return nbOfPeople;

    }

}
