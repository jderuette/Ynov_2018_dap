package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.model.AccountResponse;

/**
 *
 * @author David_tepoche
 *
 */
@Service
public class GoogleAccountService extends BaseService {

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     *
     * @param userId  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     * @throws GeneralSecurityException dunno.
     * @throws IOException              dunno.
     */
    public AccountResponse addAccount(final String userId, final HttpServletRequest request, final HttpSession session)
            throws GeneralSecurityException, IOException {
        Credential credential;
        GoogleAuthorizationCodeFlow flow;
        final AccountResponse accountResponse = new AccountResponse();

        flow = super.getFlow();
        credential = flow.loadCredential(userId);

        if (credential != null && credential.getAccessToken() != null) {
            accountResponse.setMessage("votre utilisateurs a deja les autorisations pour utiliser les services !");
        } else {
            // redirect to the authorization flow
            final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
            authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfig().getoAuth2CallbackUrl()));
            // store userId in session for CallBack Access
            session.setAttribute("userId", userId);
            authorizationUrl.setState("userId?" + userId);
            accountResponse.setRedirection(authorizationUrl.build());
        }
        return accountResponse;

    }

    /**
     * Build a current host (and port) absolute URL.
     *
     * @param req         The current HTTP request to extract schema, host, port
     *                    informations
     * @param destination the "path" to the resource
     * @return an absolute URI
     */
    protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }

    /**
     * Extract OAuth2 Google code (from URL) and decode it.
     *
     * @param request the HTTP request to extract OAuth2 code
     * @return the decoded code
     * @throws ServletException if the code cannot be decoded
     */
    private String extracCode(final HttpServletRequest request) throws ServletException {
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
            getLogger().error("Error when trying to add Google acocunt : " + responseUrl.getError());
            throw new ServletException("Error when trying to add Google acocunt");
            // onError(request, resp, responseUrl);
        }

        return decodeCode;
    }

    @Override
    protected final String getClassName() {
        return GoogleAccountService.class.getName();
    }

    /**
     * retrieve the User ID in Session.
     *
     * @param request the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    private String getUserid(final HttpServletRequest request) throws ServletException {
        final StringBuffer buf = request.getRequestURL();
        if (null != request.getQueryString()) {
            buf.append('?').append(request.getQueryString());
        }

        final AuthorizationCodeResponseUrl responseUrl = new AuthorizationCodeResponseUrl(buf.toString());
        String decodeCode = responseUrl.getState();

        decodeCode = decodeCode.substring(decodeCode.lastIndexOf("?") + 1, decodeCode.length());

        if (decodeCode == null) {
            throw new MissingServletRequestParameterException("code", "String");
        }

        if (null != responseUrl.getError()) {
            getLogger().error("Error when trying to add Google acocunt : " + responseUrl.getError());
            throw new ServletException("Error when trying to add Google acocunt");
            // onError(request, resp, responseUrl);
        }

        return decodeCode;
    }

    /**
     * Handle the Google response.
     *
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     */
    // TODO duv by Djer Bien vue de séparer Service/controller. Mais un service qui
    // dépend de "request" et "session" ce n'est pas TOP ! Essaye d'extraire les
    // données dans le controller pour les fournir au service.
    public String oAuthCallback(final String code, final HttpServletRequest request, final HttpSession session)
            throws ServletException {
        final String decodedCode = extracCode(request);

        final String redirectUri = buildRedirectUri(request, new Config().getoAuth2CallbackUrl());

        final String userId = getUserid(request);
        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, userId);
            if (null == credential || null == credential.getAccessToken()) {
                getLogger().warn("Trying to store a NULL AccessToken for user : " + userId);
            }

            if (getLogger().isDebugEnabled() && null != credential && null != credential.getAccessToken()) {
                getLogger().debug("New user credential stored with userId : " + userId + "partial AccessToken : "
                // TODO duv by Djer Attention ce token est "senssible", c'est pour ca qu'il
                // n'était pas afficher ne entier dans le code fourni !
                        + credential.getAccessToken().toString());

            }
            // onSuccess(request, resp, credential);
        } catch (IOException | GeneralSecurityException e) {
            getLogger().error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to conenct Google Account");
        }

        return "Vous etes Connecté !";
    }

}
