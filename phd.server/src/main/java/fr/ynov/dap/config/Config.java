package fr.ynov.dap.config;

public class Config {
    //TODO phd by Djer Met le commentaire Javadoc pour chaque constantes, comme ca "javadoc" places les infos au bon endroit dans le HTML généré
	/**
	 * CREDENTIALS_FILE_PATH is a variable containing the file path of the credentials
	 * TOKENS_DIRECTORY_PATH is a variable containing the directory path of the tokens
	 * APPLICATION_NAME is a variable containing the name of the application
	 */
    //TODO phd by Djer Devraient FINAL
	private final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private final String TOKENS_DIRECTORY_PATH = "tokens";
    private final String APPLICATION_NAME = "Gmail API Java Quickstart";
    
    private String applicationName;
    private String credentialsFilePath;
    private String tokensDirectoryPath;
    
    public Config() {
    	this.applicationName = APPLICATION_NAME;
    	this.credentialsFilePath = CREDENTIALS_FILE_PATH;
    	this.tokensDirectoryPath = TOKENS_DIRECTORY_PATH;
    }
    /**
     * 
     * @param applicationName The name of the application
     * @param credentialsFilePath The file path of credentials
     * @param tokensDirectoryPath The directory of the tokens 
     */
    public Config(String applicationName,String credentialsFilePath,String tokensDirectoryPath) {
    	this.applicationName = applicationName;
    	this.credentialsFilePath = credentialsFilePath;
    	this.tokensDirectoryPath = tokensDirectoryPath;
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
	 * @return the credentialsFilePath
	 */
	public String getCredentialsFilePath() {
		return credentialsFilePath;
	}
	/**
	 * @param credentialsFilePath the credentialsFilePath to set
	 */
	public void setCredentialsFilePath(String credentialsFilePath) {
		this.credentialsFilePath = credentialsFilePath;
	}
	/**
	 * @return the tokensDirectoryPath
	 */
	public String getTokensDirectoryPath() {
		return tokensDirectoryPath;
	}
	/**
	 * @param tokensDirectoryPath the tokensDirectoryPath to set
	 */
	public void setTokensDirectoryPath(String tokensDirectoryPath) {
		this.tokensDirectoryPath = tokensDirectoryPath;
	}
	/**
	 * @return the cREDENTIALS_FILE_PATH
	 */
	//TODO phd by Djer A priori pas utile ce getter sur des "static" lorsqu'on est dans une instance.
	//Si jamais il était utile de l'exposer, on "tolère" un "public" sur une constantes
	public String getCREDENTIALS_FILE_PATH() {
		return CREDENTIALS_FILE_PATH;
	}
	/**
	 * @return the tOKENS_DIRECTORY_PATH
	 */
	public String getTOKENS_DIRECTORY_PATH() {
		return TOKENS_DIRECTORY_PATH;
	}
	/**
	 * @return the aPPLICATION_NAME
	 */
	public String getAPPLICATION_NAME() {
		return APPLICATION_NAME;
	}
	
	public String getoAuth2CallbackUrl() {
		return "/oAuth2Callback";
	}

}
