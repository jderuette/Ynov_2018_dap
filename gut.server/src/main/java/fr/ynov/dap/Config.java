package fr.ynov.dap;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

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
