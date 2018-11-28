package fr.ynov.dap.dap.microsoft.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.exception.SecretFileAccesException;
import fr.ynov.dap.dap.model.MicrosoftMail;
import fr.ynov.dap.dap.model.PagedResult;

/**
 *
 * @author David_tepoche
 *
 */
@Service
public class MicrosoftMailService extends MicrosoftBaseService {
    /**
     * get the nbr total of unread mail.
     *
     * @param microsoftAccount the msaccount
     * @return nbr of mail
     * @throws IOException              throw
     * @throws SecretFileAccesException throw
     */
    public int nbrMailUnread(final MicrosoftAccount microsoftAccount) throws IOException, SecretFileAccesException {
        // Generate the token service
        return getMicrosoftService(microsoftAccount).getFolder("inbox").execute().body().getUnreadItemCount();
    }

    /**
     * get mails to display.
     *
     * @param account          the msAccount
     * @param nbrMailToDisplay nbr of mail to display.
     * @return array of microsoftMail
     * @throws IOException              throw
     * @throws SecretFileAccesException throw
     */
    public MicrosoftMail[] getMail(final MicrosoftAccount account, final int nbrMailToDisplay)
            throws IOException, SecretFileAccesException {

        PagedResult<MicrosoftMail> result = getMicrosoftService(account)
                .getMessages("inbox", null, null, nbrMailToDisplay).execute().body();
        return result.getValue();

    }

    @Override
    protected final String getClassName() {
        return MicrosoftMailService.class.getName();
    }

}
