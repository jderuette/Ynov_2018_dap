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
	@Autowired
	private PeopleAPIService peopleService;
    @Autowired
    private OutlookService outlookService;
    @Override
    protected final String getClassName() {
        return PeopleController.class.getName();
    }
    @RequestMapping("/nbPeople/{userId}")
    public final Integer getNumberOfPeople(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserException {
        AppUser user = getUserById(userId);
        Integer googleNbPeople = peopleService.getTotalPeopleAllAccount(user);
        Integer microsoftNbPeople = outlookService.getNumberOfPeoples(user);
        Integer totalPeople = googleNbPeople + microsoftNbPeople;
        return totalPeople;
    }

	@RequestMapping("/google/ncbPeople/{userId}")
	public final Integer getGoogleNumberOfPeople(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserException {
        AppUser user = getUserById(userId);
        Integer nbPeople = peopleService.getTotalPeopleAllAccount(user);
        return nbPeople;
    }
    @RequestMapping("/microsoft/nbPeople/{userId}")
    public Integer getMicrosoftNumberOfPeople(@PathVariable("userId") final String userId)
            throws UserException, IOException {
        AppUser user = getUserById(userId);
        Integer nbOfPeople = outlookService.getNumberOfPeoples(user);
        return nbOfPeople;

    }

}
