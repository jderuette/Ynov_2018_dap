package fr.ynov.dap.services;

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.http.GenericUrl;
import fr.ynov.dap.helpers.GoogleHelper;
import org.apache.logging.log4j.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@Service
public class GoogleAccountService extends GoogleHelper {

    //TODO grj by Djer Si en majuscule devrait static FINAL (et c'est une bonne chose que le LOG soit static final)
    //TODO grj by Djer La catéroy n'est pas top (manque le prefix du package)
    private static Logger LOG = LogManager.getLogger("GoogleAuth");
    public static final String OAUTH_CALLBACK_URL = "/oAuth2Callback";

    /**
     * retrieve the User ID in Session.
     *
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    public final String getUserKey(final HttpSession session) throws ServletException {
        String userKey = null;
        if (null != session && null != session.getAttribute("userKey")) {
            userKey = (String) session.getAttribute("userKey");
        }

        if (null == userKey) {
            LOG.error("userKey in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : userKey is NULL is User Session");
        }
        return userKey;
    }

    /**
     * Extract OAuth2 Google code (from URL) and decode it.
     *
     * @param request the HTTP request to extract OAuth2 code
     * @return the decoded code
     * @throws ServletException if the code cannot be decoded
     */
    public final String extractCode(final HttpServletRequest request) throws ServletException {
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
            LOG.error("Error when trying to add Google account : " + responseUrl.getError());
            throw new ServletException("Error when trying to add Google account");
            // onError(request, resp, responseUrl);
        }

        return decodeCode;
    }

    /**
     * Build a current host (and port) absolute URL.
     *
     * @param req         The current HTTP request to extract schema, host, port
     *                    information
     * @param destination the "path" to the resource
     * @return an absolute URI
     */
    public final String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }
}
