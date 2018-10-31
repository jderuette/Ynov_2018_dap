package fr.ynov.dap.dap.config;

/**
 * 
 * @author Florian BRANCHEREAU
 *
 */
public class Config {
	private static final String APPLICATION_NAME = "YNOV_GOOGLE_API_JAVA";
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    
    private String applicationName;
	private String tokensDirectoryPath;
    private String credentialsFilePath;
   

    public Config()
    {
    	applicationName = APPLICATION_NAME;
    	tokensDirectoryPath = TOKENS_DIRECTORY_PATH;
    	credentialsFilePath = CREDENTIALS_FILE_PATH;
    }
    
    /**
     * 
     * @return applicationName
     */
	public String getApplicationName() {
		return applicationName;
	}
	/**
	 * 
	 * @param application_name
	 */
	public void setApplicationName(String application_name) {
		applicationName = application_name;
	}
	/**
	 * 
	 * @return tokensDirectoryPath
	 */
	public String getTokensDirectoryPath() {
		return tokensDirectoryPath;
	}
	/**
	 * 
	 * @param tokens_directory_path
	 */
	public void setTokensDirectoryPath(String tokens_directory_path) {
		tokensDirectoryPath = tokens_directory_path;
	}
	/**
	 * 
	 * @return credentialsFilePath
	 */
	public String getCredentialsFilePath() {
		return credentialsFilePath;
	}
	/**
	 * 
	 * @param credentials_file_path
	 */
	public void setCredentialsFilePath(String credentials_file_path) {
		credentialsFilePath = credentials_file_path;
	}
	/**
	 * 
	 * @return url /oAuth2Callback
	 */
	public String getoAuth2CallbackUrl() {
		return "/oAuth2Callback";
	}  
}