package fr.ynov.dap.dap.google;

import java.io.IOException;

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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * The Class GoogleAccount.
 */
@Service
//TODO bot by Djer |POO| Semble être du "vieux code", à supprimer ? 
public class GoogleAccount extends GoogleService {
	
	/** The log. */
    //TODO bot by Djer |POO| (Ancien TO-DO non corrigé !) Final ?
	private static Logger LOG = LogManager.getLogger(GoogleAccount.class);
	
	/** The sensible data first char. */
	private final int SENSIBLE_DATA_FIRST_CHAR = 0;
	
	/** The sensible data last char. */
	private final int SENSIBLE_DATA_LAST_CHAR = 1;

	/**
	 * Instantiates a new google account.
	 */
	public GoogleAccount() {
		super();
	}

	/**
	 * O auth callback.
	 *
	 * @param code the code
	 * @param request the request
	 * @param session the session
	 * @return the string
	 * @throws ServletException the servlet exception
	 */
	//TODO bot by Djer [MVC| (Ancien TO-DO non corrigé !) Bien vue de séparer service et Controller. Mais un service qui dépend de "requets" et "session" Ce n'est pas top. Essaye d'extraire les données dans le controller, puis de fournir les données au service
	public String oAuthCallback(String code, final HttpServletRequest request,
			final HttpSession session) throws ServletException {
		
		final String decodedCode = extracCode(request);

		//FIXME bot by Djer |POO| Ne compilait pas/plus, je corrige pour pouvoir vérifier l'éxécution de ton code
		final String redirectUri = buildRedirectUri(request, getConfiguration().getRedirectUrl());
		final String userId = getUserid(session);
		try {
			final GoogleAuthorizationCodeFlow flow = super.getFlow();
			final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();
			final Credential credential = flow.createAndStoreCredential(response, userId);
			if (null == credential || null == credential.getAccessToken()) {
				LOG.warn("Trying to store a NULL AccessToken for user : " + userId);
			}

			if (LOG.isDebugEnabled()) {
				if (null != credential && null != credential.getAccessToken()) {
					LOG.debug("New user credential stored with userId : " + userId + "partial AccessToken : "
							+ credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
				}
			}
			// onSuccess(request, resp, credential);
		} catch (IOException e) {
		    //TODO bot by Djer |Log4J| Contextualise tes messages (" for userkey : " + userId)
			LOG.error("Exception while trying to store user Credential", e);
			throw new ServletException("Error while trying to conenct Google Account");
		}

		//TODO bot by Djer |MVC| (Ancien TO-DO non corrigé !) Es-tu sure d'avoir un mapping sur / ?
		return "redirect:/";
	}

	/**
	 * Gets the userid.
	 *
	 * @param session the session
	 * @return the userid
	 * @throws ServletException the servlet exception
	 */
	private String getUserid(final HttpSession session) throws ServletException {
		String userId = null;
		if (null != session && null != session.getAttribute("userId")) {
			userId = (String) session.getAttribute("userId");
		}

		if (null == userId) {
			LOG.error("userId in Session is NULL in Callback");
			throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
		}
		return userId;
	}

	/**
	 * Extrac code.
	 *
	 * @param request the request
	 * @return the string
	 * @throws ServletException the servlet exception
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
			LOG.error("Error when trying to add Google acocunt : " + responseUrl.getError());
			throw new ServletException("Error when trying to add Google acocunt");
			// onError(request, resp, responseUrl);
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
	
    /**
     * Adds the account.
     *
     * @param userId the user id
     * @param request the request
     * @param session the session
     * @return the string
     */
    public String addAccount(String userId, final HttpServletRequest request,
            final HttpSession session) {
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        try {
            flow = super.getFlow();
            credential = flow.loadCredential(userId);

            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
              //FIXME bot by Djer |POO| Ne compilait pas/plus, je corrige pour pouvoir vérifier l'éxécution de ton code
                authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfiguration().getRedirectUrl()));
                session.setAttribute("userId", userId);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
          //TODO bot by Djer |Log4J| Contextualise tes messages (" for userkey : " + userId)
            LOG.error("Error while loading credential (or Google Flow)", e);
        }
        return response;
    }
}
