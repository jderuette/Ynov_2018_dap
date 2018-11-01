package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.oauth2.model.Userinfoplus;

import fr.ynov.dap.dto.out.NextEventOutDto;
import fr.ynov.dap.google.AccountService;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.model.CalendarEvent;

/**
 * Controller to manage every call to Google Calendar API.
 * @author Kévin Sibué
 *
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController extends BaseController {

    /**
     * Instance of Calendar service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * Instance of Account service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AccountService accountService;

    /**
     * Return calendar service instance.
     * @return CalendarService instance
     */
    private CalendarService getCalendarService() {
        return calendarService;
    }

    /**
     * Return google account service instance.
     * @return GoogleAccountService instance
     */
    private AccountService getAccountService() {
        return accountService;
    }

    /**
     * Endpoint to get the user's next event.
     * @param userId User's Id
     * @return Next event for user linked by userId
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    @RequestMapping("/nextEvent/{userId}")
    public final NextEventOutDto getNextEvent(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException {

        Userinfoplus userInfo = getAccountService().getUserInfo(userId);

        if (userInfo == null) {
            throw new NullPointerException("Unknow user");
        }

        String userEmail = userInfo.getEmail();

        //TODO sik by Djer Comme calculer le "status de l'utilsiateur" est obligatoire,
        // tu pourrais faire ce traitement dans le CalendarService ?
        //Attention cependant, évite d'appeler un Service (UserInfoPlus) à partir
        // d'un autre (CalendarService) !
        CalendarEvent evnt = getCalendarService().getNextEvent(userId);

        return new NextEventOutDto(evnt, userEmail);

    }

    @Override
    protected final String getClassName() {
        return CalendarController.class.getName();
    }

}
