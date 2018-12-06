package fr.ynov.dap.dap;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.springframework.context.annotation.PropertySource;

/**
 * The Class Config.
 */
@PropertySource("classpath:config.properties")
public class Config {

	/** The client secret dir. */
    //TODO bot by Djer |Design Patern| Inutiel ton "credentialsFilePath" contient le chemin en entier
	private String clientSecretDir;

	/** The application name. */
	private String applicationName;

	/** The tokens directory path. */
	private String tokensDirectoryPath;

	/** The credentials file path. */
	private String credentialsFilePath;

	/** The redirect url google. */
	private String redirectUrlGoogle;

	/** The Constant CONFIG_FILE_PATH. */
	//TODO bot by Djer |Design Patern| Domage que je ne puisse pa configurer CET propriété ... 
	private static final String CONFIG_FILE_PATH = 
	        //TODO bot by Djer |POO| Utiliser System.getProperty("") pour rester compatible sur d'autres OS que celui sur lequel tu developpe
			System.getProperty("user.home") + "\\dap\\bot\\config.properties";

	/**
	 * Instantiates a new config.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/**
	 * @throws IOException 
	 * 
	 */
	public Config() throws IOException {
		InputStreamReader configStream = new InputStreamReader(
				new FileInputStream(CONFIG_FILE_PATH),
				Charset.forName("UTF-8"));

		this.clientSecretDir = "google/client";
		this.applicationName = "Hoc Dap";
		this.tokensDirectoryPath = "tokens";
		//TODO bot by Djer |POO| Utiliser System.getProperty("") pour rester compatible sur d'autres OS que celui sur lequel tu developpe
		this.credentialsFilePath = System.getProperty("user.home") + "\\credentials.json";
		this.redirectUrlGoogle = "/oAuth2Callback";
		
		if (null != configStream) {
			Properties configProps = new Properties();
			try {
				configProps.load(configStream);
				//TODO bot by Djer |Design Patern| Et si je decide de ne pas valoriser UNE des propriété dnas le fichier ? Cela écrase par "null" ? 
				this.clientSecretDir = configProps.getProperty("client_secret_dir");
				this.applicationName = configProps.getProperty("application_name");
				this.tokensDirectoryPath = configProps.getProperty("tokens_directory_path");
				this.credentialsFilePath = System.getProperty("user.home") + 
						configProps.getProperty("credentials_file_path");
				this.redirectUrlGoogle = configProps.getProperty("redirect_url");
			} finally {
				configStream.close();
			}
		}
	}

	/**
	 * Sets the client secret dir.
	 *
	 * @param clientSecretDir the new client secret dir
	 */
	public void setClientSecretDir(String clientSecretDir) {
		this.clientSecretDir = clientSecretDir;
	}

	/**
	 * Sets the application name.
	 *
	 * @param applicationName the new application name
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * Sets the tokens directory path.
	 *
	 * @param tokensDirectoryPath the new tokens directory path
	 */
	public void setTokensDirectoryPath(String tokensDirectoryPath) {
		this.tokensDirectoryPath = tokensDirectoryPath;
	}

	/**
	 * Sets the credentials file path.
	 *
	 * @param credentialsFilePath the new credentials file path
	 */
	public void setCredentialsFilePath(String credentialsFilePath) {
		this.credentialsFilePath = credentialsFilePath;
	}

	/**
	 * Gets the client secret dir.
	 *
	 * @return the client secret dir
	 */
	public String getClientSecretDir() {
		return clientSecretDir;
	}

	/**
	 * Gets the application name.
	 *
	 * @return the application name
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * Gets the tokens directory path.
	 *
	 * @return the tokens directory path
	 */
	public String getTokensDirectoryPath() {
		return tokensDirectoryPath;
	}

	/**
	 * Gets the credentials file path.
	 *
	 * @return the credentials file path
	 */
	public String getCredentialsFilePath() {
		return credentialsFilePath;
	}

	/**
	 * Gets the o auth 2 callback url.
	 *
	 * @return the o auth 2 callback url
	 */
	public String getRedirectUrl() {
		return redirectUrlGoogle;
	}

	/**
	 * Sets the redirect url.
	 *
	 * @param redirectUrl the new redirect url
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrlGoogle = redirectUrl;
	}

}
