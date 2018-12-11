package fr.ynov.dap.services.microsoft;

import com.fasterxml.jackson.databind.*;
import fr.ynov.dap.helpers.MicrosoftAuthHelper;
import fr.ynov.dap.models.common.User;
import fr.ynov.dap.models.microsoft.MicrosoftAccount;
import fr.ynov.dap.repositories.UserRepository;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MicrosoftContactService {

    /**
     * Autowired UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LogManager.getLogger(MicrosoftContactService.class);

    /**
     * Get all user microsoft account unread email count
     *
     * @param userName userName
     * @return total unread email
     */
    public int getNumberUnreadEmails(String userName) {

        int contactCount = 0;

        try {
            User user = userRepository.findByName(userName);
            if (user == null) {
              //TODO grj by Djer |Gestion Exception| Contexte (for userName : +userName).
                //TODO grj by Djer |Log4J| une petite log ?
                throw new Exception("User does not exist");
            }

            List<MicrosoftAccount> userMicrosoftAccountList = user.getMicrosoftAccountList();
            for (MicrosoftAccount currentMicrosoftAccount : userMicrosoftAccountList) {
                MicrosoftAuthHelper.ensureTokens(currentMicrosoftAccount);
                OutlookService outlookService = OutlookServiceBuilder.getOutlookService(currentMicrosoftAccount.getToken(), currentMicrosoftAccount.getEmail());

                ObjectMapper mapper   = new ObjectMapper();
                JsonNode     response = mapper.valueToTree(outlookService.getNumberContacts().execute().body());

                contactCount += response.get("@odata.count").asInt();
            }

        } catch (Exception e) {
          //TODO grj by Djer |Log4J| Contexte (for userName : +userName).
            LOGGER.error("Error when trying to count all MicrosoftAccount unread mails", e);
          //TODO grj by Djer |Log4J| "e.printStackTrace()" affiche directement dans la console, la pile est déja présente dans ton message de log. Supprime la ligne ci-dessous
            e.printStackTrace();
        }

        return contactCount;
    }


}
