package fr.ynov.dap;

public class Config {

	/**
	 * Initial config Google
	 */
	private String applicationName = "Gmail API Java Quickstart";;
	private String tokensPath = "tokens";;
	private String credentialsPath = "/credentials.json";;
	private String auth2CallbackUrl = "/oAuth2Callback";;

	/**
	 * Initial config Microsoft
	 */
	private String authority = "https://login.microsoftonline.com";
	private String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";

	/*
	 * Default config constructor
	 */
	public Config() {

	}

	/**
	 * Config constructor for ZERO CONF
	 * 
	 * @param applicationName
	 * @param tokensPath
	 * @param credentialsPath
	 * @param auth2CallbackUrl
	 */
	public Config(String googleApplicationName, String googleTokensPath, String googleCredentialsPath, String googleAuth2CallbackUrl,
			String microsoftAuthority, String microsodftAuthorizeUrl) {
		this.applicationName = googleApplicationName;
		this.tokensPath = googleTokensPath;
		this.credentialsPath = googleCredentialsPath;
		this.auth2CallbackUrl = googleAuth2CallbackUrl;

		this.authority = microsoftAuthority;
		this.authorizeUrl = microsodftAuthorizeUrl;
	}

	public String getApplicationName() {
		return applicationName;
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

	public String getCredentialsPath() {
		return credentialsPath;
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
