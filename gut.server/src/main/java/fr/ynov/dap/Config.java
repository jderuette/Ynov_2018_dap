package fr.ynov.dap;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

<<<<<<< HEAD
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

@Component
//TODO gut by Djer Principe "ZeroConf" à revoir, ici tu as du "noConf" ! 
public class Config {
	private static final String CREDENTIALS_FOLDER = "/credentials.json";
	private static final InputStream CLIENT_SECRET_DIR = GmailService.class.getResourceAsStream(CREDENTIALS_FOLDER);
	private static final String APPLICATION_NAME = "HoC DaP";
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static NetHttpTransport http_transport = null;

	/**
	 * url de retour pour l'authentification Google
	 */
	private static final String OAUTH2_CALLBACK_URL = "/oAuth2Callback";

	/**
	 * connection à Google
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Config() throws IOException, GeneralSecurityException {
		http_transport = GoogleNetHttpTransport.newTrustedTransport();
	}

	/**
	 * path pour les crédentials
	 * @return String
	 */
	public String getCredentialsFolder() {
		return CREDENTIALS_FOLDER;
	}

	/**
	 * Path pour le client secret dir
	 * @return InputStream
	 */
	public InputStream getClientSecretDir() {
		return CLIENT_SECRET_DIR;
=======
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import fr.ynov.dap.service.google.GmailService;

@Component
//TODO gut by Djer Principe "ZeroConf" à revoir, ici tu as du "noConf" ! 
public class Config {
	private static String CREDENTIALS_FOLDER = "/credentials.json";
	private static InputStream CLIENT_SECRET_DIR = GmailService.class.getResourceAsStream(CREDENTIALS_FOLDER);
	private static String APPLICATION_NAME = "HoC DaP";
	private static String TOKENS_DIRECTORY_PATH = "tokens";
	private static NetHttpTransport http_transport = null;
	private static final Logger logger = LogManager.getLogger();
	
	
	public Config(String _credentialsFolder,
			String _applicationName,
			String _TokensDirecotryPath) 
			throws IOException, GeneralSecurityException {
		logger.debug("Chargement d'une configuration personnalisee");
		http_transport = GoogleNetHttpTransport.newTrustedTransport();
		CREDENTIALS_FOLDER = _credentialsFolder;
		APPLICATION_NAME = _applicationName;
		TOKENS_DIRECTORY_PATH = _TokensDirecotryPath;
	}
	
	/**
	 * url de retour pour l'authentification Google
	 */
	private static final String OAUTH2_CALLBACK_URL = "/oAuth2Callback";

	/**
	 * connection à Google
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Config() throws IOException, GeneralSecurityException {
		logger.debug("Chargement de la configuration par defaut");
		http_transport = GoogleNetHttpTransport.newTrustedTransport();
	}

	/**
	 * path pour les crédentials
	 * @return String
	 */
	public String getCredentialsFolder() {
		return CREDENTIALS_FOLDER;
	}

	/**
	 * Path pour le client secret dir
	 * @return InputStream
	 */
	public InputStream getClientSecretDir() {
		return CLIENT_SECRET_DIR; 
>>>>>>> refs/heads/master
	}
	/**
	 * Returne le nom de l'application
	 * @return String
	 */
	public String getApplicationName() {
		return APPLICATION_NAME;
	}
	/**
	 * Passerelle de connexion pour Google
	 * @return NetHTTPTransport
	 */
	public NetHttpTransport getHTTP_TRANSPORT() {
		return http_transport;
	}

	/**
	 * Init du transport
	 * @param hTTP_TRANSPORT
	 */
	public static void setHTTP_TRANSPORT(NetHttpTransport hTTP_TRANSPORT) {
		http_transport = hTTP_TRANSPORT;
	}

	/**
	 * Récupère le path du token directory
	 * @return
	 */
	public String getTokensDirectoryPath() {
		return TOKENS_DIRECTORY_PATH;
	}

	/**
	 * Url de retour pour l'add Account
	 * @return
	 */
	public String getoAuth2CallbackUrl() {
		return OAUTH2_CALLBACK_URL;
	}
}
