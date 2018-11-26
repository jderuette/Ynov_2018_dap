package fr.ynov.dap.services.microsoft;

import com.fasterxml.jackson.databind.*;
import fr.ynov.dap.helpers.MicrosoftAuthHelper;
import fr.ynov.dap.models.*;
import fr.ynov.dap.repositories.UserRepository;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * MicrosoftMailService
 */
@Service
public class MicrosoftMailService {

    /**
     * Autowired UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LogManager.getLogger(MicrosoftMailService.class);

    /**
     * Get all user microsoft account unread email count
     *
     * @param userName userName
     * @return total unread email
     */
    public int getNumberUnreadEmails(String userName) {

        int emailCount = 0;

        try {
            User user = userRepository.findByName(userName);
            if (user == null) {
                throw new Exception("User does not exist");
            }

            List<MicrosoftAccount> userMicrosoftAccountList = user.getMicrosoftAccountList();
            for (MicrosoftAccount currentMicrosoftAccount : userMicrosoftAccountList) {
                MicrosoftAuthHelper.ensureTokens(currentMicrosoftAccount);
                OutlookService outlookService = OutlookServiceBuilder.getOutlookService(currentMicrosoftAccount.getToken(), currentMicrosoftAccount.getEmail());

                ObjectMapper mapper   = new ObjectMapper();
                JsonNode     response = mapper.valueToTree(outlookService.getUnread().execute().body());

                emailCount += response.get("unreadItemCount").asInt();
            }

        } catch (Exception e) {
            LOGGER.error("Error when trying to count all MicrosoftAccount unread mails", e);
            e.printStackTrace();
        }

        return emailCount;
    }

}
