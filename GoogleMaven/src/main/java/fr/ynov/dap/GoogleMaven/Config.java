package fr.ynov.dap.GoogleMaven;




//TODO elj by Djer |Design Patern| Externalisation de la configuration ? 
public class Config {
	private static final String CREDENTIALS_FILE = System.getProperty("user.home")
            + System.getProperty("file.separator") + "Credential" + System.getProperty("file.separator")
            + "credentials.json";
	private static final String CLIENT_SECRET_DIR = "tokens";
	private static final String APPLICATION_NAME = "HoCDaP";
	private static final String AUTH_CALLBACK = "/oAuth2Callback";
	public String applicationName;
	public String credentialFolder;
	public String clientSecretFile;
	public String authcallback;
	
	 public Config (){
           this.applicationName=APPLICATION_NAME;
           this.clientSecretFile=CLIENT_SECRET_DIR;
           this.credentialFolder=CREDENTIALS_FILE;
           this.authcallback=AUTH_CALLBACK;
	}

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @param applicationName the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * @return the credentialFolder
	 */
	public String getCredentialFolder() {
		return credentialFolder;
	}

	/**
	 * @param credentialFolder the credentialFolder to set
	 */
	public void setCredentialFolder(String credentialFolder) {
		this.credentialFolder = credentialFolder;
	}

	/**
	 * @return the clientSecretFile
	 */
	public String getClientSecretFile() {
		return clientSecretFile;
	}

	/**
	 * @param clientSecretFile the clientSecretFile to set
	 */
	public void setClientSecretFile(String clientSecretFile) {
		this.clientSecretFile = clientSecretFile;
	}

	public String getoAuth2CallbackUrl() {
		// TODO Auto-generated method stub
		return authcallback;
	}

	public String getAuthcallback() {
		return authcallback;
	}

	public void setAuthcallback(String authcallback) {
		this.authcallback = authcallback;
	}

	public String getTokensDirectoryPath() {
		// TODO Auto-generated method stub
		return clientSecretFile;
	}
	
}