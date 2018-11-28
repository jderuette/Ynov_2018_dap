package fr.ynov.dap.dap.microsoft.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.exception.SecretFileAccesException;
import fr.ynov.dap.dap.model.MicrosoftEvent;

/**
 *
 * @author David_tepoche
 *
 */
@Service
public class MicrosoftEventService extends MicrosoftBaseService {
    /**
     * get nbr of event from the date from.
     *
     * @param microsoftAccount the msAccount
     * @param dateFrom         date
     * @param maxResult        result max to display
     * @return array of microsoft event
     * @throws IOException              throw from
     * @throws SecretFileAccesException throw
     */
    public MicrosoftEvent[] getEvent(final MicrosoftAccount microsoftAccount, final String dateFrom,
            final Integer maxResult) throws IOException, SecretFileAccesException {

        return getMicrosoftService(microsoftAccount)
                .getEvents("start/dateTime DESC", "start/dateTime  GE '" + dateFrom + "'", null, maxResult).execute()
                .body().getValue();
    }

    @Override
    protected final String getClassName() {
        return MicrosoftEventService.class.getName();
    }
}
