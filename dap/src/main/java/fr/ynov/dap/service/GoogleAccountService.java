package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.data.dao.GoogleAccountRepository;
import fr.ynov.dap.data.dao.MicrosoftAccountRepository;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccount;

@Controller
/**
 * Permet de gérer l'authentification des comptes google.
 * @author abaracas
 *
 */
//TODO baa by Djer |SOA| Séparation entre service et Controller. A défaut de "séparer" cette classe est un controller et devrait etre dans le package "web" et avoir un nom qui ne prete pas à confusion
//TODO baa by Djer |IDE| Configure les "save action" de ton IDE pour qu'il format ton code (et organise les import) lors de la sauvegarde
public class GoogleAccountService extends GoogleService {
    //TODO baa by Djer |POO| Si tu ne précise pas de modifier sur l'attribut, alors il aura le même que la classe qui le contient (ici "public"). Il devrait etre private
@Autowired AppUserRepository appUserRepository;
//TODO baa by Djer |POO| Si tu ne précise pas de modifier sur l'attribut, alors il aura le même que la classe qui le contient (ici "public"). Il devrait etre private
@Autowired GoogleAccountRepository googleAccountRepository;
//TODO baa by Djer |POO| Si tu ne précise pas de modifier sur l'attribut, alors il aura le même que la classe qui le contient (ici "public"). Il devrait etre private
@Autowired MicrosoftAccountRepository microsoftAccountRepository;
//TODO baa by Djer |Log4J| Devrait être final (la (pseudo) référence ne sera pas modifiée)
private static Logger LOG = LogManager.getLogger();
    /**
     * Constructeur
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    public GoogleAccountService() throws GeneralSecurityException, IOException {
	super();
	// TODO Auto-generated constructor stub
    }    

    /**
     * Handle the Google response.
     * 
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
	    final HttpSession session) throws ServletException, GeneralSecurityException {
	final String decodedCode = extracCode(request);

	final String redirectUri = buildRedirectUri(request, maConfig.getUrl());

	final String userId = getUserid(session);
	try {
	    final GoogleAuthorizationCodeFlow flow = super.getFlow();
	    final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

	    final Credential credential = flow.createAndStoreCredential(response, userId);
	    if (null == credential || null == credential.getAccessToken()) {
		LOG.warn("Essaie de charger un credential NULL pour l'utilisateur : " + userId);
	    }

	    if (LOG.isDebugEnabled()) {
		if (null != credential && null != credential.getAccessToken()) {
		    //TODO baa by Djer |Log4J| Attention le token est une information senssible, et bien que c'est indiqué dans le message ce qui est affiché n'est pas "partial". Utilise un substring
		    LOG.info("Nouvel utilisateur : " + userId + "partial AccessToken : "
			    + credential.getAccessToken());
		}
		
		//TODO baa by Djer |API Google| Tu devrais sauvegarder le lien appuser -> Google acocunt par ici (il te faudra aussi extraire le userkey ("userid" contient en faite ton accountName))
	    }
	} catch (IOException e) {
	    //TODO baa by Djer |Log4J| N'ajoute pas l'exception dans ton message de cette façon, elle sera au mieux "mal affichée". Utilise le deuxième paramère ("cause") et laisse Log4J présenter correctement cette excetpion dans les logs
	    LOG.error("Exception lors du chargement du credential du user : " + userId + " Erreur : " + e);
	    throw new ServletException("Error while trying to conenct Google Account");
	}

	return "redirect:/";
    }

    /**
     * retrieve the User ID in Session.
     * 
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    private String getUserid(final HttpSession session) throws ServletException {
	String userId = null;
	if (null != session && null != session.getAttribute("userId")) {
	    userId = (String) session.getAttribute("userId");
	}

	if (null == userId) {
	    LOG.error("userId in Session is NULL in Callback");
	    throw new ServletException("Error when trying to add Google account : userId is NULL is User Session");
	}
	return userId;
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
	    LOG.error("Error when trying to add Google account : " + responseUrl.getError());
	    throw new ServletException("Error when trying to add Google acocunt");
	    // onError(request, resp, responseUrl);
	}

	return decodeCode;
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
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param userKey  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @param accountName name of user account
     * @return the view to Display (on Error)
     * @throws GeneralSecurityException  exception
     */
    @RequestMapping("/add/googleAccount/{userKey}/{accountName}")
    public String addGoogleAccount(@PathVariable("userKey") final String userKey,@PathVariable("accountName") final String accountName, final HttpServletRequest request,
	    final HttpSession session) throws GeneralSecurityException {
	//TODO baa by Djer |JPA| AppUser est "maitre" de la relation vers GoogleAccount. Il vaut mieux : 1-Extraire l'utilisateur, 2-modifier/ajouter le compte Google dans l'objet extrait, 3- Sauvegarder l'utilisateur (JPA s'occupera de créer/modifier les entitées liées)
	AppUser user = appUserRepository.findByUserkey(userKey);
	googleAccountRepository.save(new GoogleAccount(user, accountName));
	String response = "errorOccurs";
	GoogleAuthorizationCodeFlow flow;
	Credential credential = null;
	try {
	    flow = super.getFlow();
	    credential = flow.loadCredential(accountName);

	    if (credential != null && credential.getAccessToken() != null) {
		response = "Le compte est déjà connu, pas besoin de l'ajouter.";
	    } else {
		// redirect to the authorization flow
		final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
		authorizationUrl.setRedirectUri(buildRedirectUri(request, maConfig.getUrl()));
		// store userId in session for CallBack Access
		session.setAttribute("userId", accountName);
		response = "redirect:" + authorizationUrl.build();
	    }
	} catch (IOException e) {
	    LOG.error("Erreur lors du chargement du Credential", e);
	}
	//TODO baa by Djer |POO| Ce commentaire n'est plus vrai
	// only when error occurs, else redirected BEFORE
	return response;
    }
}
