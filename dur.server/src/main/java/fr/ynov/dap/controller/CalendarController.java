package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.microsoft.OutlookService;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * Controller to manage every call to Google Calendar API.
 * @author Robin DUDEK
 *
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController extends BaseController {

    /**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Instance of Calendar service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * Instance of Outlook service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private OutlookService outlookService;

    @Override
    protected final String getClassName() {
        return CalendarController.class.getName();
    }

    @RequestMapping("/nextEvent/{userKey}")
    public final String getNextEvent(@PathVariable("userKey") final String userKey, final ModelMap model)
            throws GeneralSecurityException, IOException {

        AppUser user = appUserRepository.findByUserKey(userKey);

        Event gEvent = calendarService.getNextEvent(user);
        fr.ynov.dap.microsoft.entity.Event msEvent = outlookService.getNextEvent(user);

        if (gEvent == null && msEvent == null) {
            return null;
        } else if (gEvent == null && msEvent != null) {
            model.addAttribute("event", msEvent);
        } else if ((gEvent != null && msEvent == null)
                || (gEvent.getStart().getDateTime().getValue() < msEvent.getStart().getDateTime().getTime())) {
            model.addAttribute("event", gEvent);
        } else {
            model.addAttribute("event", msEvent);
        }

        return "calendar";

    }

    /**
     * Endpoint to get the user's next event.
     * @param userId User's Id
     * @return Next event for user linked by userId
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    @RequestMapping("/google/nextEvent/{userKey}")
    public final String getGoogleNextEvent(@PathVariable("userKey") final String userKey, final ModelMap model)
            throws GeneralSecurityException, IOException {

        AppUser user = appUserRepository.findByUserKey(userKey);
        Event event = calendarService.getNextEvent(user);
        model.addAttribute("event", event);
        return "calendar";
    }

    /**
     * Endpoint to get the user's next event.
     * @param userId User's Id
     * @return Next event for user linked by userId
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    @RequestMapping("/microsoft/nextEvent/{userKey}")
    public String getMicrosoftNextEvent(@PathVariable("userKey") final String userId, final ModelMap model)
            throws IOException {

        AppUser user = GetUserById(userId);

        fr.ynov.dap.microsoft.entity.Event event = outlookService.getNextEvent(user);
        model.addAttribute("event", event);
        return "calendar";

    }
}
