package fr.ynov.dap.dap.microsoft.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.exception.SecretFileAccesException;
import okhttp3.ResponseBody;

/**
 *
 * @author David_tepoche
 *
 */
@Service
public class MicrosoftContactService extends MicrosoftBaseService {

    /**
     * get the nbr of contact for msAccount.
     *
     * @return nbr of contact
     * @param microsoftAccount the mAccount
     * @throws SecretFileAccesException throw if the service fail.
     * @throws IOException              throw if load of credential fail of the sett
     *                                  of redirection fail.
     */
    public Integer getNbrContact(final MicrosoftAccount microsoftAccount) throws IOException, SecretFileAccesException {
        // get the reponseBody because MS send only the nomber of contact without json
        // etc ...
        ResponseBody e = getMicrosoftService(microsoftAccount).getNbrContact().execute().body();
        if (e != null && !e.string().isEmpty()) {
            Integer nbrContact = (Integer.valueOf(e.string()));
            return nbrContact;
        } else {
            getLogger().error(
                    "le count des contact microsoft a renvoyé une reponse null" + microsoftAccount.getAccountName());
            return 0;
        }
    }

    @Override
    protected final String getClassName() {
        return MicrosoftMailService.class.getName();
    }
}
