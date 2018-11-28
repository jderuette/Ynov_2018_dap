package fr.ynov.dap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class Config.
 *
 * @author clement.brossette
 */
@Primary
@Configuration
@PropertySource("classpath:config.properties")
public class Config {
	
	/** The application name. */
	@Value("${dap.application_name}")
	private String applicationName;
	  
    /** The tokens directory path. */
	@Value("${dap.tokens_directory_path}")
    private String tokensDirectoryPath;
    
    /** The credentials file path. */
	@Value("${dap.credential_file_path}")
    private String credentialsFilePath;

	/**
     * Gets the application name.
     *
     * @return the application name
     */
    public final String getApplicationName() {
		return applicationName;
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
	 * Gets the tokens directory path.
	 *
	 * @return the tokens directory path
	 */
	public final String getTokensDirectoryPath() {
		return tokensDirectoryPath;
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
	 * Credentials file path.
	 *
	 * @return the string
	 */
	public final String getCredentialsFilePath() {
		return credentialsFilePath;
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
	 * Gets the o auth 2 callback url.
	 *
	 * @return the o auth 2 callback url
	 */
	public String getoAuth2CallbackUrl() {
		// TODO Auto-generated method stub
		return "/oAuth2Callback";
	}
}
