/*
 * Leo BARBARY
 */
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

    @Autowired
    private Config configuration;

    @Autowired
    private GoogleAccountService googleAccountService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private MicrosoftAccountService msAccountService;
    
    @Override
    protected final String getClassName() {
        return AccountController.class.getName();
    }

    @RequestMapping(value = "/account/add/{userKey}") //, method = RequestMethod.POST)
    public AppUser addNewUser(@PathVariable final String userKey) {

        AppUser newUser = new AppUser();
        newUser.setUserKey(userKey);

        newUser = appUserRepository.save(newUser);

        return newUser;

    }

    //TODO bal by Djer |Rest API| On a toujours passé le userKey en RequestParam pourquoi ne pas continuer ? La seul exception est le "addUser" car on CREE le User, ensuite on y fait référence via le RequestParam. C'est bien de rester "cohérent" dans ton API public.
    @RequestMapping(value = "/account/google/add/{gAccountName}/{userId}") //, method = RequestMethod.POST)
    public String addGoogleAccount(@PathVariable final String gAccountName, @PathVariable final String userId,
            final HttpServletRequest request, final HttpSession session, final HttpServletResponse response)
            throws GeneralSecurityException, UserException, IOException {
        AppUser userAccount = appUserRepository.findByUserKey(userId);
        if (userAccount == null) {
            throw new UserException();
        }
        String redirectUri = buildRedirectUri(request, configuration.getOAuth2CallbackUrl());
        AddAccountResponse accountRes = googleAccountService.addAccount(gAccountName, userId, redirectUri);
        if (accountRes != null) {
            if (accountRes.getIsSuccess()) {
                session.setAttribute(Constant.SESSION_USER_ID, userId);
                session.setAttribute(Constant.SESSION_ACCOUNT_NAME, gAccountName);
                String url = accountRes.getRedirectUrl();
                response.sendRedirect(url);
                return "";
            } 
        }
        throw new NullPointerException("No result for current action");

    }

    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session)
            throws ServletException, GeneralSecurityException {
        final String decodedCode = extractGoogleCode(request);
        final String redirectUri = buildRedirectUri(request, configuration.getOAuth2CallbackUrl());
        final String userId = session.getAttribute(Constant.SESSION_USER_ID).toString();
        final String gAccountName = session.getAttribute(Constant.SESSION_ACCOUNT_NAME).toString();
        //FIME bal by Djer |POO| Es-tu sur de l'ordre de tes paramètres en l'appelant et la signature de la méthode ?? 
        return googleAccountService.oAuthCallback(code, userId, gAccountName, decodedCode, redirectUri);

    }

  //TODO bal by Djer |Rest API| On a toujours passé le userKey en RequestParam pourquoi ne pas continuer ? La seul est axception est le "addUser" car on CREE le User, ensuite on y fait référence via le RequestParam. C'est bien de rester "cohérent" dans ton API public.
    @RequestMapping(value = "/account/microsoft/add/{msAccountName}/{userId}") //, method = RequestMethod.POST
    public void addMicrosoftAccount(@PathVariable final String msAccountName, @PathVariable final String userId,
            final HttpServletRequest request, final HttpSession session, final HttpServletResponse response)
            throws UserException, IOException {

        AppUser userAccount = appUserRepository.findByUserKey(userId);
        if (userAccount == null) {
            throw new UserException();
        }
        UUID state = UUID.randomUUID();
		UUID none = UUID.randomUUID();

        session.setAttribute(Constant.SESSION_EXPECTED_STATE, state);
		session.setAttribute(Constant.SESSION_EXPECTED_NONCE, none);
        session.setAttribute(Constant.SESSION_USER_ID, userId);
        session.setAttribute(Constant.SESSION_ACCOUNT_NAME, msAccountName);

		String redirectUrl = msAccountService.getLoginUrl(state, none);

        response.sendRedirect(redirectUrl);

    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(@RequestParam("code") final String code, @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state, final HttpServletRequest request) throws UserException,
              IOException {

        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute(Constant.SESSION_EXPECTED_STATE);
        UUID expectedNonce = (UUID) session.getAttribute(Constant.SESSION_EXPECTED_NONCE);
        String userId = (String) session.getAttribute(Constant.SESSION_USER_ID);
        String accountName = (String) session.getAttribute(Constant.SESSION_ACCOUNT_NAME);
        
        //TODO bal by Djer |POO| Une bonne partie de ce code pourrait etre dans un "MicrosoftAccountService" pour rester cohérent avec la structure du code "Google" écrit avant.
        if (state.equals(expectedState)) {
            Token idTokenObj = Token.parseEncodedToken(idToken, expectedNonce.toString());
            if (idTokenObj != null) {
                TokenResponse tokenResponse = msAccountService.getTokenFromAuthCode(code, idTokenObj.getTenantId());
                AppUser currentUser = appUserRepository.findByUserKey(userId);
                if (currentUser == null) {
                    throw new UserException();
                }

                MicrosoftAccount msAccount = new MicrosoftAccount();
                msAccount.setToken(tokenResponse);
                msAccount.setTenantId(idTokenObj.getTenantId());
                msAccount.setToken(tokenResponse);
                msAccount.setEmail(idTokenObj.getEmail());
                msAccount.setAccountName(accountName);
                currentUser.addMicrosoftAccount(msAccount);
                appUserRepository.save(currentUser);

            } 

        } 

        return "Vous êtes connecté !";

    }

    @RequestMapping("/microsoft/logout")
    public Boolean logout(final HttpServletRequest request, final HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.invalidate();
        Boolean isLogged = false;
        return isLogged;

    }

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
    

	protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
		final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath(destination);
		return url.build();
	}

}
