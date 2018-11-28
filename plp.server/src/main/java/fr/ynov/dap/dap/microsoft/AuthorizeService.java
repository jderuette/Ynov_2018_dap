package fr.ynov.dap.dap.microsoft;

import fr.ynov.dap.dap.CalendarService;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.microsoft.OutlookAccount;
import fr.ynov.dap.dap.data.microsoft.Token;
import fr.ynov.dap.dap.microsoft.models.*;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import fr.ynov.dap.dap.repositories.OutlookAccountRepository;
import fr.ynov.dap.dap.repositories.TokenRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthorizeService {
    /**
     * Instantiate Logger.
     */
    private static final Logger LOG = LogManager.getLogger(CalendarService.class);

    @Autowired
    OutlookAccountRepository outlookAccountRepository;

    @Autowired
    AppUserRepository userRepository;



    public Map<String, String> authorize(String code, String idToken, UUID state, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute("expected_state");
        UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

        String userKey = "";
        String accountName =  "";

        try {
            userKey = getUserKey(session);
            accountName = getAccountName(session);
        }catch (Exception e) {
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
                    outlookAccountRepository.save(outlookAccount);
                    userRepository.save(user);
                }
//                session.setAttribute("tokens", tokenResponse);
//                session.setAttribute("userConnected", true);
//                session.setAttribute("userName", idTokenObj.getName());
//                // Get user info
//                OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken());
//                OutlookUser user;
//                try {
//                    user = outlookService.getCurrentUser().execute().body();
//                    session.setAttribute("userEmail", user.getMail());
//                } catch (IOException e) {
//                    session.setAttribute("error", e.getMessage());
//                    LOG.error("Error when trying to get session for", e);
//                }
//                session.setAttribute("userTenantId", idTokenObj.getTenantId());
            } else {
//                session.setAttribute("error", "ID token failed validation.");
                LOG.error("ID token failed validation with id : " + idToken);
            }
        } else {
//            session.setAttribute("error", "Unexpected state returned from authority.");
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
            throw new ServletException("Error when trying to add Google account : accountName is NULL is User Session");
        }
        return accountName;
    }

    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "logout";
    }
}
