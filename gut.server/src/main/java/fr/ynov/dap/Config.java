package fr.ynov.dap;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import fr.ynov.dap.service.google.GmailService;

@Component
//TODO gut by Djer |Design Patern| (ancien TO-DO) Principe "ZeroConf" à revoir, ici tu as du "noConf" ! 
public class Config {
    //TODO gut by Djer |POO| Attention si tu laisse en static, tu ne peu avoir qu'UNE seul configuration (ce n'est pas trop genant dans notre cas)
	private static String CREDENTIALS_FOLDER = "/credentials.json";
	private static InputStream CLIENT_SECRET_DIR = GmailService.class.getResourceAsStream(CREDENTIALS_FOLDER);
	private static String APPLICATION_NAME = "HoC DaP";
	private static String TOKENS_DIRECTORY_PATH = "tokens";
	private static NetHttpTransport http_transport = null;
	private static final Logger logger = LogManager.getLogger();
	
	//TODO gut by Djer |Design Patern| Pour appliquer le prinbcipe ZeroConf tu DOIS avoir (1)des variable (attributs) qui (2)ont une valeur par defaut "raisonablement juste
	
	//TODO gut by Djer |Design Patern| Externalisation de la configuration ? Si tu as des variables tu pourras les mettres à jour via une configuration externe
	
	public Config(String _credentialsFolder,
			String _applicationName,
			String _TokensDirecotryPath) 
			throws IOException, GeneralSecurityException {
		logger.debug("Chargement d'une configuration personnalisee");
		//TODO gut by Djer |POO| C'est TRES étrange d'initialiser des attributs **static** dans un constructeur (qui a pour role de créer une **instance** alors que le mot clef "static" sert justement acréer des attributs **non** lié à une instance)
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
	//TODO gut by Djer |POO| Ca n'est pas interdit, mais un getter "d'instance" qui renvoie un attribut static c'est très étrange ! (et piegeux !)
	public String getCredentialsFolder() {
		return CREDENTIALS_FOLDER;
	}

	/**
	 * Path pour le client secret dir
	 * @return InputStream
	 */
	public InputStream getClientSecretDir() {
		return CLIENT_SECRET_DIR; 
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
