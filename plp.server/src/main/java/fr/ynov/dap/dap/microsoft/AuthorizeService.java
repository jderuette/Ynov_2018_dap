package fr.ynov.dap.dap.microsoft;

import fr.ynov.dap.dap.CalendarService;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.microsoft.OutlookAccount;
import fr.ynov.dap.dap.data.microsoft.Token;
import fr.ynov.dap.dap.microsoft.models.IdToken;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import fr.ynov.dap.dap.repositories.OutlookAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthorizeService {
    /**
     * Instantiate Logger.
     */
    private static final Logger LOG = LogManager.getLogger(CalendarService.class);
    //TODO plp by Djer |POO| Attention si tu ne précise pas, par defaut cette attribut est public (comme la classe) !
    /**
     * Instantiate OutlookAccountRepository.
     */
    @Autowired
    OutlookAccountRepository outlookAccountRepository;
  //TODO plp by Djer |POO| Attention si tu ne précise pas, par defaut cette attribut est public (comme la classe) !
    /**
     * Instantiate AppUserRepository.
     */
    @Autowired
    AppUserRepository userRepository;

    /**
     * @param code
     * @param idToken
     * @param state
     * @param request
     * @return
     */
    //TODO plp by Djer |MVC| Evite de psser des objets "Web" dans tes service (HttpServletRequest) ou de les manipuler (HttpSession). Fait extraire les infos utiles par le controller
    public Map<String, String> authorize(String code, String idToken, UUID state, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute("expected_state");
        UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

        //TODO plp by Djer |POO| Evite d'initialiser les chaines de caractère. De plus ta méthode "getUserKey" écrase par "null" si pas de valeur ....
        String userKey = "";
      //TODO plp by Djer |POO| Evite d'initialiser les chaines de caractère. De plus ta méthode "getUserKey" écrase par "null" si pas de valeur ....
        String accountName = "";

        try {
            userKey = getUserKey(session);
            accountName = getAccountName(session);
        } catch (Exception e) {
            LOG.error("can't get userKey or accountName", e);
        }

        if (state.equals(expectedState)) {
            IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
            if (idTokenObj != null) {
                Token tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());

                AppUser user = userRepository.findByName(userKey);
                if (outlookAccountRepository.findByName(accountName) == null) {
                    OutlookAccount outlookAccount = new OutlookAccount();
                    outlookAccount.setName(accountName);
                    outlookAccount.setTenantId(idTokenObj.getTenantId());
                    outlookAccount.setToken(tokenResponse);
                    user.addOutlookAccount(outlookAccount);
                    //TODO plp by Djer |JPA| Ton AppUser à un "Cascade.ALL" sur "outlookAccount". JPA va donc automatiquement créer/mettre à jour les "Microsfot Account", pas utile que le fasse avant
                    outlookAccountRepository.save(outlookAccount);
                    userRepository.save(user);
                }
            } else {
                LOG.error("ID token failed validation with id : " + idToken);
            }
        } else {
            //TODO plp by Djer |Log4J| Contexualise tes messages (" expected : " + expectedState + " recieved : " + state)
            LOG.error("unexpected state returned from authority");
        }

        Map<String, String> response = new HashMap<>();
        response.put("authCode", code);
        response.put("idToken", idToken);
        return response;
    }

    /**
     * retrieve the User ID in Session.
     *
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    private String getUserKey(final HttpSession session) throws ServletException {
        String userKey = null;
        if (null != session && null != session.getAttribute("userKey")) {
            userKey = (String) session.getAttribute("userKey");
        }

        if (null == userKey) {
            LOG.error("userId in Session is NULL in Callback");
            //TODO plp by Djer |POO| Evite les copier/Coller, tu aurais put déplacer cette méthode dans uneclasse utilitaire (ici ton message indique du "Google" alors que ce n'est même pas vrai !)
            throw new ServletException("Error when trying to add Google account : userKey is NULL is User Session");
        }
        return userKey;
    }

    /**
     * retrieve the account name in Session.
     *
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    private String getAccountName(final HttpSession session) throws ServletException {
        String accountName = null;
        if (null != session && null != session.getAttribute("accountName")) {
            accountName = (String) session.getAttribute("accountName");
        }

        if (null == accountName) {
            LOG.error("userId in Session is NULL in Callback");
          //TODO plp by Djer |POO| extraire dans uen classe utilitaire ? 
            throw new ServletException("Error when trying to add Google account : accountName is NULL is User Session");
        }
        return accountName;
    }

    /**
     * @param request
     * @return
     */
    //TODO plp by Djer |MVC| Evite de faire dépendre tes services d'object "Web"
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "logout";
    }
}
