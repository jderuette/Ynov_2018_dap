package fr.ynov.dap.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * The Class Config.
 */
//TODO mot by Djer |Design Patern| Externalisation de la configuration ? 
public class Config {

    //TODO mot by Djer |POO| Si écrit en Majusucule devrait être static final
	/** The application name. */
	private final String APPLICATION_NAME = "Google Calendar API Java Quickstart";

	/** The tokens directory path. */
	private final String TOKENS_DIRECTORY_PATH = "tokens";

	/** The credentials file path. */
	private final String CREDENTIALS_FILE_PATH = "/credentials.json";

	/** The Auth 2 callback url. */
	private final String Auth2CallbackUrl = "/oAuth2Callback";

	//TODO mot by Djer |IDE| Ton IDE te dit que ca n'est pas utilisé. Bug ? A supprimer ? 
	/** The separator. */
	private String separator = System.getProperty("file.separator");

	//TODO mot by Djer |IDE| Ton IDE te dit que ca n'est pas utilisé. Bug ? A supprimer ? 
	/** The home path. */
	private String homePath = System.getProperty("user.home");
	
	private final String AUTHORITY = "https://login.microsoftonline.com";
	private final String AUTHORIZEURL = AUTHORITY + "/common/oauth2/v2.0/authorize";

	

	private String applicationName;
	private String tokensPath;
	private String credentialPath;
	private String auth2CallbackUrl;
	private String authority;
	private String authorizeUrl;

	public Config() {
		this.applicationName = APPLICATION_NAME;
		this.tokensPath = TOKENS_DIRECTORY_PATH;
		this.credentialPath = CREDENTIALS_FILE_PATH;
		this.auth2CallbackUrl = Auth2CallbackUrl;
		this.authority = AUTHORITY;
		this.authorizeUrl=AUTHORIZEURL;
	}

	/**
	 * Constructor Config.
	 *
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */

	public String getApplicationName() {
		return applicationName;
	}

	public Config(String separator, String homePath, String applicationName, String tokensPath, String credentialPath,
			String auth2CallbackUrl, String authority, String authorizeUrl) {
		this.separator = separator;
		this.homePath = homePath;
		this.applicationName = applicationName;
		this.tokensPath = tokensPath;
		this.credentialPath = credentialPath;
		this.auth2CallbackUrl = auth2CallbackUrl;
		this.authority = authority;
		this.authorizeUrl=authorizeUrl;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getTokensPath() {
		return tokensPath;
	}

	public void setTokensPath(String tokensPath) {
		this.tokensPath = tokensPath;
	}

	public String getCredentialPath() {
		return credentialPath;
	}

	public void setCredentialPath(String credentialPath) {
		this.credentialPath = credentialPath;
	}

	public String getAuth2CallbackUrl() {
		return auth2CallbackUrl;
	}

	public void setAuth2CallbackUrl(String auth2CallbackUrl) {
		this.auth2CallbackUrl = auth2CallbackUrl;
	}
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getAuthorizeUrl() {
		return authorizeUrl;
	}

	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}
}
