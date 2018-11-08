package fr.ynov.dap.helpers;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.*;
import com.google.api.services.people.v1.*;
import fr.ynov.dap.*;
import fr.ynov.dap.services.GoogleAccountService;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * GoogleHelper
 */
@Component
public class GoogleHelper {

    @Autowired
    private Configuration configuration;

    @Autowired
    private GoogleAccountService googleAccountService;

    private              JsonFactory      JSON_FACTORY;
    private              List<String>     SCOPES;
    private              NetHttpTransport HTTP_TRANSPORT;
    private static final Logger           LOG = LogManager.getLogger(GoogleHelper.class);

    public GoogleHelper() {
        JSON_FACTORY = JacksonFactory.getDefaultInstance();
        SCOPES = Arrays.asList(GmailScopes.GMAIL_LABELS, CalendarScopes.CALENDAR_READONLY, PeopleServiceScopes.CONTACTS_READONLY);
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            LOG.error("Error when trying to get HTTP Transport", e);
        }
    }

    /**
     * @return Google Authorization Code Flow
     * @throws IOException Deal with exception
     */
    public GoogleAuthorizationCodeFlow getFlow() throws IOException {

        InputStream         in            = Launcher.class.getResourceAsStream(configuration.getCredentialFolder());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(configuration.getClientSecretDir())))
                .setAccessType("offline")
                .build();
    }


    /**
     * Creates an authorized Credential object.
     *
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(String userKey) throws IOException {

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = getFlow();
        return flow.loadCredential(userKey);
    }

    /**
     * Retrieve GMAIL service
     *
     * @param userKey userKey to log
     * @return GMAIL
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    public Gmail getGmailService(String userKey) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(configuration.getApplicationName())
                .build();
    }

    /**
     * Retrieve People Service
     *
     * @param userKey userKey to log
     * @return People Service by Google
     * @throws IOException              Exception
     * @throws GeneralSecurityException Exception
     */
    public PeopleService getPeopleService(String userKey) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(configuration.getApplicationName())
                .build();
    }

    /**
     * Retrieve CALENDAR service
     *
     * @param userKey userKey to log
     * @return CALENDAR
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    public com.google.api.services.calendar.Calendar getCalendarService(String userKey) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(configuration.getApplicationName())
                .build();
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

    public String loadCredential(final HttpServletRequest request, final HttpSession session, String userName, String googleAccountName) {

        GoogleAuthorizationCodeFlow flow;
        Credential                  credential;
        final String                OAUTH_CALLBACK_URL = "/oAuth2Callback";
        String                      response           = "";

        try {
            flow = getFlow();
            credential = flow.loadCredential(googleAccountName);
            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                // redirect to the authorization flow
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request, OAUTH_CALLBACK_URL));
                // store userKey in session for CallBack Access
                session.setAttribute("googleAccount", googleAccountName);
                session.setAttribute("userName", userName);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
            LOG.error("Error while loading credential (or Google Flow)", e);
        }

        return response;

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
        final String                       decodeCode  = responseUrl.getCode();

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


}
