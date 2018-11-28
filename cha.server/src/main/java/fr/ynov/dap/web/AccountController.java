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
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.Config;
import fr.ynov.dap.Constant;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.exception.UserException;
import fr.ynov.dap.google.GoogleAccountService;
import fr.ynov.dap.microsoft.MicrosoftAccountService;
import fr.ynov.dap.microsoft.Token;
import fr.ynov.dap.model.AddAccountResponse;
import fr.ynov.dap.repository.AppUserRepository;

@RestController
public class AccountController extends BaseController {

    /**
     * Current configuration.
     */
    @Autowired
    private Config configuration;

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
    private MicrosoftAccountService microsoftAccountService;
    
    @Override
    protected final String getClassName() {
        return AccountController.class.getName();
    }

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
     * @param googleAccountName this user account name
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
    @RequestMapping(value = "/account/google/add/{googleAccountName}/{userId}")
    public String addGoogleAccount(@PathVariable final String googleAccountName, @PathVariable final String userId,
            final HttpServletRequest request, final HttpSession session, final HttpServletResponse response)
            throws GeneralSecurityException, UserException, IOException {

        AppUser userAccount = appUserRepository.findByUserKey(userId);

        if (userAccount == null) {
            throw new UserException();
        }

        String redirectUri = buildRedirectUri(request, configuration.getOAuth2CallbackUrl());

        AddAccountResponse accountRes = googleAccountService.addAccount(googleAccountName, userId, redirectUri);

        if (accountRes != null) {

            if (accountRes.getIsSuccess()) {

                session.setAttribute(Constant.SESSION_USER_ID, userId);
                session.setAttribute(Constant.SESSION_ACCOUNT_NAME, googleAccountName);

                String url = accountRes.getRedirectUrl();

                response.sendRedirect(url);

                return "";

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
            throws ServletException, GeneralSecurityException {

        final String decodedCode = extractGoogleCode(request);
        final String redirectUri = buildRedirectUri(request, configuration.getOAuth2CallbackUrl());
        final String userId = session.getAttribute(Constant.SESSION_USER_ID).toString();
        final String googleAccountName = session.getAttribute(Constant.SESSION_ACCOUNT_NAME).toString();

        return googleAccountService.oAuthCallback(code, userId, googleAccountName, decodedCode, redirectUri);

    }

    /**
     * Link new Microsoft account to a user.
     * @param microsoftAccountName Microsoft Account name
     * @param userId User id
     * @param request Http request
     * @param session Http session
     * @param response Http response
     * @throws UserNotFoundException Exception
     * @throws IOException Exception
     */
    @RequestMapping(value = "/account/microsoft/add/{microsoftAccountName}/{userId}") //, method = RequestMethod.POST
    public void addMicrosoftAccount(@PathVariable final String microsoftAccountName, @PathVariable final String userId,
            final HttpServletRequest request, final HttpSession session, final HttpServletResponse response)
            throws UserException, IOException {

        AppUser userAccount = appUserRepository.findByUserKey(userId);

        if (userAccount == null) {
            throw new UserException();
        }

        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        session.setAttribute(Constant.SESSION_EXPECTED_STATE, state);
        session.setAttribute(Constant.SESSION_EXPECTED_NONCE, nonce);
        session.setAttribute(Constant.SESSION_USER_ID, userId);
        session.setAttribute(Constant.SESSION_ACCOUNT_NAME, microsoftAccountName);

        String redirectUrl = microsoftAccountService.getLoginUrl(state, nonce);

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
            @RequestParam("state") final UUID state, final HttpServletRequest request) throws UserException,
              IOException {

        HttpSession session = request.getSession();

        UUID expectedState = (UUID) session.getAttribute(Constant.SESSION_EXPECTED_STATE);
        UUID expectedNonce = (UUID) session.getAttribute(Constant.SESSION_EXPECTED_NONCE);
        String userId = (String) session.getAttribute(Constant.SESSION_USER_ID);
        String accountName = (String) session.getAttribute(Constant.SESSION_ACCOUNT_NAME);

        if (state.equals(expectedState)) {

            Token idTokenObj = Token.parseEncodedToken(idToken, expectedNonce.toString());

            if (idTokenObj != null) {

                TokenResponse tokenResponse = microsoftAccountService.getTokenFromAuthCode(code, idTokenObj.getTenantId());

                AppUser currentUser = appUserRepository.findByUserKey(userId);

                if (currentUser == null) {
                    throw new UserException();
                }

                MicrosoftAccount microsoftAccount = new MicrosoftAccount();
                microsoftAccount.setToken(tokenResponse);
                microsoftAccount.setTenantId(idTokenObj.getTenantId());
                microsoftAccount.setToken(tokenResponse);
                microsoftAccount.setEmail(idTokenObj.getEmail());
                microsoftAccount.setAccountName(accountName);

                currentUser.addMicrosoftAccount(microsoftAccount);

                appUserRepository.save(currentUser);

            } 

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
    public Boolean logout(final HttpServletRequest request, final HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.invalidate();

        Boolean isLogged = false;

        return isLogged;

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
    
    /**
	 * Builds the redirect uri.
	 *
	 * @param req the req
	 * @param destination the destination
	 * @return the string
	 */
	protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
		final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath(destination);
		return url.build();
	}

}
