package fr.ynov.dap.dap.Configuration;

/**
 * 
 * @author Mon_PC
 * Class config 
 *
 */
public class Config {
	private static final String APPLICATION_NAME = "Ynov app Java";
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String CREDENTIALS_FILE_PATH = "/web_credentials.json";
    
    private String applicationName;
	private String tokensDirectoryPath;
    private String credentialsFilePath;
   
    /**
     * Constructor of the class Config
     */
    public Config()
    {
    	applicationName = APPLICATION_NAME;
    	tokensDirectoryPath = TOKENS_DIRECTORY_PATH;
    	credentialsFilePath = CREDENTIALS_FILE_PATH;
    }
    /**
     * 
     * @return Application Name
     * Return the application name
     */
	public String getApplicationName() {
		return applicationName;
	}
	/**
	 * @param application_name
	 * Set the name of the application
	 */
	public void setApplicationName(String application_name) {
		applicationName = application_name;
	}
	/**
	 * @return tokens directory path
	 * Name of the directory path
	 */
	public String getTokensDirectoryPath() {
		return tokensDirectoryPath;
	}
	/**
	 * @param tokens_directory_path
	 * Set the name of the token directory path
	 */
	public void setTokensDirectoryPath(String tokens_directory_path) {
		tokensDirectoryPath = tokens_directory_path;
	}
	/**
	 * @return Credentials file path
	 * return name credentials file path
	 */
	public String getCredentialsFilePath() {
		return credentialsFilePath;
	}
	/**
	 * 
	 * @param credentials_file_path
	 * set name credentials file path
	 */
	public void setCredentialsFilePath(String credentials_file_path) {
		credentialsFilePath = credentials_file_path;
	}
	/**
	 * 
	 * @return "/oAuth2Callback"
	 * redirection path
	 */
	public String getoAuth2CallbackUrl() {
		return "/oAuth2Callback";
	}  
}