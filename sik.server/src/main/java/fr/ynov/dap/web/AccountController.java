package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;

import fr.ynov.dap.Config;
import fr.ynov.dap.Constants;
import fr.ynov.dap.contract.AppUserRepository;
import fr.ynov.dap.dto.out.SessionOutDto;
import fr.ynov.dap.exception.AddAccountFailedException;
import fr.ynov.dap.exception.InvalidStateException;
import fr.ynov.dap.exception.InvalidTokenException;
import fr.ynov.dap.exception.MissingSessionParameterException;
import fr.ynov.dap.exception.UserNotFoundException;
import fr.ynov.dap.google.GoogleAccountService;
import fr.ynov.dap.microsoft.model.IdToken;
import fr.ynov.dap.microsoft.model.TokenResponse;
import fr.ynov.dap.microsoft.service.MicrosoftAccountService;
import fr.ynov.dap.model.AddAccountResult;
import fr.ynov.dap.model.AppUser;
import fr.ynov.dap.model.microsoft.MicrosoftAccount;
import fr.ynov.dap.utils.StrUtils;
import fr.ynov.dap.utils.UrlUtils;

/**
 * Controller to manage every call to Google Account API.
 * @author Kévin Sibué
 *
 */
@RestController
public class AccountController extends BaseController {

    /**
     * Current configuration.
     */
    @Autowired
    private Config config;

    /**
     * Instance of GoogleAccount service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private GoogleAccountService googleAccountService;

    /**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Instance of MicrosoftAccountService.
     * Auto resolved by Autowire.
     */
    @Autowired
    private MicrosoftAccountService msAccountService;

    /**
     * Add new user.
     * @param userKey user Key
     * @return Html page
     */
    @RequestMapping(value = "/account/add/{userKey}")
    public AppUser addNewUser(@PathVariable final String userKey) {

        AppUser newUser = new AppUser();
        newUser.setUserKey(userKey);

        newUser = appUserRepository.save(newUser);

        return newUser;

    }

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param gAccountName this user account name
     * @param userId  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @param response the Http Response
     * @return the view to Display (on Error)
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws UserNotFoundException Exception
     * @throws AddAccountFailedException Exception
     * @throws IOException Exception
     */
    @RequestMapping(value = "/account/google/add/{gAccountName}/{userId}")
    public String addGoogleAccount(@PathVariable final String gAccountName, @PathVariable final String userId,
            final HttpServletRequest request, final HttpSession session, final HttpServletResponse response)
            throws GeneralSecurityException, UserNotFoundException, AddAccountFailedException, IOException {

        AppUser userAccount = appUserRepository.findByUserKey(userId);

        if (userAccount == null) {
            throw new UserNotFoundException();
        }

        String redirectUri = UrlUtils.buildRedirectUri(request, config.getOAuth2CallbackUrl());

        AddAccountResult accountRes = googleAccountService.addAccount(gAccountName, userId, redirectUri);

        if (accountRes != null) {

            if (accountRes.getIsSuccess()) {

                session.setAttribute(Constants.SESSION_USER_ID, userId);
                session.setAttribute(Constants.SESSION_ACCOUNT_NAME, gAccountName);

                String url = accountRes.getRedirectUrl();

                response.sendRedirect(url);

                //TODO sik by Djer |POO| Il n'est pas nécéssaire de reonvoyer un résultat. Tu peux faire comme pour l'ajout de compte Microsoft
                return "";

            } else {

                //TODO sik by Djer |Gestion Exception| Ajouter le "setErrorDescription" dans le message ?
                throw new AddAccountFailedException();

            }

        }

        throw new NullPointerException("No result for current action");

    }

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws MissingSessionParameterException Thrown when a session parameter missing.
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session)
            throws ServletException, GeneralSecurityException, MissingSessionParameterException {

        final String decodedCode = extractGoogleCode(request);
        final String redirectUri = UrlUtils.buildRedirectUri(request, config.getOAuth2CallbackUrl());
        final String userId = session.getAttribute(Constants.SESSION_USER_ID).toString();
        final String gAccountName = session.getAttribute(Constants.SESSION_ACCOUNT_NAME).toString();

        return googleAccountService.oAuthCallback(code, userId, gAccountName, decodedCode, redirectUri);

    }

    /**
     * Link new Microsoft account to a user.
     * @param msAccountName Microsoft Account name
     * @param userId User id
     * @param request Http request
     * @param session Http session
     * @param response Http response
     * @throws UserNotFoundException Exception
     * @throws IOException Exception
     */
    @RequestMapping(value = "/account/microsoft/add/{msAccountName}/{userId}")
    public void addMicrosoftAccount(@PathVariable final String msAccountName, @PathVariable final String userId,
            final HttpServletRequest request, final HttpSession session, final HttpServletResponse response)
            throws UserNotFoundException, IOException {

        AppUser userAccount = appUserRepository.findByUserKey(userId);

        if (userAccount == null) {
            throw new UserNotFoundException();
        }

        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        session.setAttribute(Constants.SESSION_EXPECTED_STATE, state);
        session.setAttribute(Constants.SESSION_EXPECTED_NONCE, nonce);
        session.setAttribute(Constants.SESSION_USER_ID, userId);
        session.setAttribute(Constants.SESSION_ACCOUNT_NAME, msAccountName);

        String redirectUrl = msAccountService.getLoginUrl(state, nonce);

        response.sendRedirect(redirectUrl);

    }

    /**
     * Callback for Microsoft account auth.
     * @param code Code
     * @param idToken Token
     * @param state State
     * @param request Request
     * @return Html page
     * @throws UserNotFoundException Exception.
     * @throws MissingSessionParameterException Exception.
     * @throws InvalidTokenException Exception.
     * @throws InvalidStateException Exception.
     * @throws IOException Exception
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(@RequestParam("code") final String code, @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state, final HttpServletRequest request) throws UserNotFoundException,
            MissingSessionParameterException, InvalidTokenException, InvalidStateException, IOException {

        HttpSession session = request.getSession();

        UUID expectedState = (UUID) session.getAttribute(Constants.SESSION_EXPECTED_STATE);
        UUID expectedNonce = (UUID) session.getAttribute(Constants.SESSION_EXPECTED_NONCE);
        String userId = (String) session.getAttribute(Constants.SESSION_USER_ID);
        String accountName = (String) session.getAttribute(Constants.SESSION_ACCOUNT_NAME);

        if (expectedNonce == null || expectedState == null || StrUtils.isNullOrEmpty(userId)
                || StrUtils.isNullOrEmpty(accountName)) {
            throw new MissingSessionParameterException();
        }

        if (state.equals(expectedState)) {

            IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());

            if (idTokenObj != null) {

                TokenResponse tokenResponse = msAccountService.getTokenFromAuthCode(code, idTokenObj.getTenantId());

                AppUser currentUser = appUserRepository.findByUserKey(userId);

                if (currentUser == null) {
                    throw new UserNotFoundException();
                }

                MicrosoftAccount msAccount = new MicrosoftAccount();
                msAccount.setToken(tokenResponse);
                msAccount.setTenantId(idTokenObj.getTenantId());
                msAccount.setToken(tokenResponse);
                msAccount.setEmail(idTokenObj.getEmail());
                msAccount.setAccountName(accountName);

                currentUser.addMicrosoftAccount(msAccount);

                appUserRepository.save(currentUser);

            } else {
                
                throw new InvalidTokenException();

            }

        } else {

            throw new InvalidStateException(state);

        }

        return "Vous êtes connecté !";

    }

    /**
     * Logout for Microsoft services.
     * @param request Http request
     * @param response Http response
     * @return .
     */
    @RequestMapping("/microsoft/logout")
    public SessionOutDto logout(final HttpServletRequest request, final HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.invalidate();

        SessionOutDto outDto = new SessionOutDto();
        outDto.setLogged(false);

        return outDto;

    }

    @Override
    protected final String getClassName() {
        return AccountController.class.getName();
    }

    /**
     * Extract OAuth2 Google code (from URL) and decode it.
     * @param request the HTTP request to extract OAuth2 code
     * @return the decoded code
     * @throws ServletException if the code cannot be decoded
     */
    private String extractGoogleCode(final HttpServletRequest request) throws ServletException {

        final StringBuffer buf = request.getRequestURL();

        if (null != request.getQueryString()) {
            buf.append('?').append(request.getQueryString());
        }

        final AuthorizationCodeResponseUrl responseUrl = new AuthorizationCodeResponseUrl(buf.toString());
        final String decodeCode = responseUrl.getCode();

        if (decodeCode == null) {
            throw new MissingServletRequestParameterException("code", "String");
        }

        if (null != responseUrl.getError()) {

            getLogger().error("Error when trying to add Google account : " + responseUrl.getError());

            throw new ServletException("Error when trying to add Google account");

        }

        return decodeCode;

    }

}
