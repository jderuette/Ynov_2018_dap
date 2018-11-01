package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.dto.out.LoginResponseOutDto;
import fr.ynov.dap.model.LoginStatusEnum;

/**
 * Controller to manage google accounts.
 * @author Kévin Sibué
 *
 */
@Service
public class GoogleAccountService extends GoogleAPIService {

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    //TODO sik by Djer Bien de l'avoir transféré en Service. Mais un service métier qui a besoin d'une "Request" C'est pas top.
    //TODO sik by Djer essaye de trovuer une moyen d'extraire tout ce qui concerne la requete dans le controller.
    public String oAuthCallback(final String code, final HttpServletRequest request)
            throws ServletException, GeneralSecurityException {

        final String decodedCode = extracCode(request);
        final String redirectUri = buildRedirectUri(request, getConfig().getOAuth2CallbackUrl());
        final String userId = extractUserId(request);

        try {

            final GoogleAuthorizationCodeFlow flow = super.getFlows();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, userId);
            if (null == credential || null == credential.getAccessToken()) {
                getLogger().warn("Trying to store a NULL AccessToken for user : " + userId);
            }

            if (getLogger().isDebugEnabled() && null != credential && null != credential.getAccessToken()) {
                getLogger().debug("New user credential stored with userId : " + userId + "partial AccessToken : "
                        + credential.getAccessToken());
                //.substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
            }

        } catch (IOException e) {

            getLogger().error("Exception while trying to store user Credential", e);

            throw new ServletException("Error while trying to conenct Google Account");

        }

        return "Vous êtes connecté au service DaP";

    }

    /**
     * Extract OAuth2 Google code (from URL) and decode it.
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

            getLogger().error("Error when trying to add Google account : " + responseUrl.getError());

            throw new ServletException("Error when trying to add Google account");

        }

        return decodeCode;

    }

    /**
     * Extract OAuth2 Google state (from URL) and decode it.
     * @param request the HTTP request to extract OAuth2 state
     * @return the decoded state
     * @throws ServletException if the state cannot be decoded
     */
    private String extractUserId(final HttpServletRequest request) throws ServletException {

        final StringBuffer buf = request.getRequestURL();

        if (null != request.getQueryString()) {
            buf.append('?').append(request.getQueryString());
        }

        final AuthorizationCodeResponseUrl responseUrl = new AuthorizationCodeResponseUrl(buf.toString());
        final String decodeState = responseUrl.getState();

        if (decodeState == null) {
            throw new MissingServletRequestParameterException("state", "String");
        }

        String[] stateParts = decodeState.split("=");

        if (null != responseUrl.getError() || stateParts.length < 1) {

            getLogger().error("Error when trying to add Google account : " + responseUrl.getError());

            throw new ServletException("Error when trying to add Google account");

        }

        return stateParts[1];

    }

    /**
     * Build a current host (and port) absolute URL.
     * @param req         The current HTTP request to extract schema, host, port
     *                    informations
     * @param destination the "path" to the resource
     * @return an absolute URI
     */
    //TODO sik by Djer essaye de supprimer cette request du service metier.
    protected String buildRedirectUri(final HttpServletRequest req, final String destination) {

        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);

        return url.build();

    }

    @Override
    protected final String getClassName() {
        return GoogleAccountService.class.getName();
    }

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param userId  the user to store Data
     * @param request the HTTP request
     * @return the view to Display (on Error)
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    //TODO sik by Djer essaye de supprimer cette request du service metier.
    public LoginResponseOutDto addAccount(final String userId, final HttpServletRequest request)
            throws GeneralSecurityException {

        LoginResponseOutDto outDto = new LoginResponseOutDto();

        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;

        try {

            flow = super.getFlows();

            credential = flow.loadCredential(userId);

            if (credential != null && credential.getAccessToken() != null) {

                outDto.setStatus(LoginStatusEnum.ALREADY_ADDED);

            } else {

                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfig().getOAuth2CallbackUrl()));
                authorizationUrl.setState("userId=" + userId);

                outDto.setStatus(LoginStatusEnum.REDIRECTION);
                outDto.setUrl(authorizationUrl.build());

            }

        } catch (IOException e) {

            getLogger().error("Error while loading credential (or Google Flow)", e);

            outDto.setStatus(LoginStatusEnum.ERROR);

        }

        return outDto;

    }

}
