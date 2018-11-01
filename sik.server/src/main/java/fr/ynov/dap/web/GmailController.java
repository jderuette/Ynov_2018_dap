package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dto.out.UnreadMailOutDto;
import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.google.GMailService;

/**
 * Controller to manage every call to Google Gmail API.
 * @author Kévin Sibué
 *
 */
@RestController
@RequestMapping("/gmail")
public class GmailController extends BaseController {

    /**
     * Instance of Gmail service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private GMailService gmailService;

    /**
     * Return gmail service instance.
     * @return GmailService instance
     */
    private GMailService getGmailService() {
        return gmailService;
    }

    /**
     * Endpoint to get this number of unread message.
     * @param userId User's Id
     * @return Number of unread mail for user linked by userId. JSON Formatted.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when no configuration provided.
     */
    @RequestMapping("/nbUnread/{userId}")
    public final UnreadMailOutDto getNumberOfUnreadMessage(@PathVariable("userId") final String userId)
            throws NoConfigurationException, IOException, GeneralSecurityException {

        Integer nb = getGmailService().getNbUnreadEmails(userId);

        return new UnreadMailOutDto(nb);

    }

    @Override
    protected final String getClassName() {
        return GmailController.class.getName();
    }

}
