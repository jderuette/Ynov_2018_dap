package fr.ynov.dap.web;

import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.exceptions.AuthorizationException;
import fr.ynov.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.microsoft.auth.IdToken;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * Microsoft Account controller used to create a new Microsoft Account.
 */
@Controller
public class MicrosoftAccountController {

    /**
     * Logger used for logs.
     */
    private static Logger log = LogManager.getLogger();

    /**
     * UserKey parameter constant.
     */
    private static final String USER_KEY_PARAM_NAME = "userKey";
    /**
     * Account name parameter constant.
     */
    private static final String ACCOUNT_NAME_PARAM_NAME = "accountName";

    /**
     * Random generated value that represents the expected state.
     */
    private static final String EXPECTED_STATE_ATTRIBUTE_NAME = "expected_state";

    /**
     * Arbitrary number used for cryptographic communication.
     */
    private static final String EXPECTED_NONCE_ATTRIBUTE_NAME = "expected_nonce";

    /**
     * AppUser repository instantiate thanks to the injection of dependency.
     */
    @Autowired
    private AppUserRepository repository;

    /**
     * Add a Google account (user will be prompt to connect in the Web Browser and accept required
     * access).
     * @param accountName  the user to store Data
     * @param userKey the userKey of the user
     * @param request the HTTP request
     * @return the view to Display (on Error)
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/account/add/microsoft/{accountName}")
    public String addAccount(@PathVariable final String accountName,
            @RequestParam(USER_KEY_PARAM_NAME) final String userKey,
            final HttpServletRequest request) throws GeneralSecurityException {
        String response = "errorOccurs";

        String infosToLog = new StringBuilder()
        .append("New Microsoft account creation with AccountName=")
        .append(accountName)
        .append(" and userKey=").append(userKey).toString();
        log.info(infosToLog);

        AppUser appUser = repository.findByUserKey(userKey);
        if (appUser == null) {
            response = "The account name doesn't exist.";
            log.warn(response);
            return response;
        }

        if (appUser.getMicrosoftAccountNames().contains(accountName)) {
            response = "The provided accountName already exists";
            log.warn(response);
            return response;
        }

        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

     // Save the state and nonce in the session so we can verify after the auth process redirects back
        HttpSession session = request.getSession();
        session.setAttribute(EXPECTED_STATE_ATTRIBUTE_NAME, state);
        session.setAttribute(EXPECTED_NONCE_ATTRIBUTE_NAME, nonce);
        session.setAttribute(ACCOUNT_NAME_PARAM_NAME, accountName);
        session.setAttribute(USER_KEY_PARAM_NAME, userKey);
        String loginUrl = AuthHelper.getLoginUrl(state, nonce);

        response = "redirect:" + loginUrl;

        return response;
    }

    /**
     * Authorize callback used when the user has been logged to his Microsoft Account.
     * @param model model to display to the view.
     * @param code authCode
     * @param idToken id of the token
     * @param state state
     * @param request used to get the session.
     * @return the view to display.
     * @throws AuthorizationException exception
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(final ModelMap model,
            @RequestParam("code") final String code,
            @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state,
            final HttpServletRequest request) throws AuthorizationException {

        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute(EXPECTED_STATE_ATTRIBUTE_NAME);
        UUID expectedNonce = (UUID) session.getAttribute(EXPECTED_NONCE_ATTRIBUTE_NAME);

        if (!state.equals(expectedState)) {
            model.addAttribute("error", "Unexpected state returned from authority.");
            return "redirect:/"; //TODO
        }

        IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
        if (idTokenObj == null) {
            model.addAttribute("error", "ID token failed validation.");
            return "redirect:/"; //TODO
        }

        TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());

        final String accountName = (String) session.getAttribute(ACCOUNT_NAME_PARAM_NAME);
        final String userKey = (String) session.getAttribute(USER_KEY_PARAM_NAME);

        MicrosoftAccount microsoftAccount = new MicrosoftAccount();
        microsoftAccount.setAccountName(accountName);
        try {
            microsoftAccount.setTokenResponse(tokenResponse);
            microsoftAccount.setIdToken(idTokenObj);
        } catch (JsonProcessingException jpe) {
            throw new AuthorizationException("Failed to store the token response or the token id.", jpe);
        }

        AppUser appUser = repository.findByUserKey(userKey);
        microsoftAccount.setOwner(appUser);
        appUser.addMicrosoftAccount(microsoftAccount);
        repository.save(appUser);

        return "redirect:/"; //TODO
    }
}
