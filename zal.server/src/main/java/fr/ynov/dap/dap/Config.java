package fr.ynov.dap.dap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class Config.
 */
@Primary
@Configuration
@PropertySource("classpath:spring.properties")
public class Config {

	/** The application name. */
	@Value("${application_name}")
	private String applicationName;

	/** The token directory path. */
	@Value("${credentials_token_path}")
	private String tokenDirectoryPath;

	/** The credential file path. */
	@Value("${credentials_file_path}")
	private String credentialFilePath;

	/** The client secret file. */
	@Value("${credentials_json}")
	private String clientSecretFile;

	/**
	 * Gets the client secret file.
	 *
	 * @return the client secret file
	 */
	public String getClientSecretFile() {
		return clientSecretFile;
	}

	/**
	 * Instantiates a new config.
	 */
	public Config() {
	}

	/**
	 * Gets the application name.
	 *
	 * @return the application name
	 */
	public final String getApplicationName() {
		return applicationName;
	}

	/**
	 * Gets the token directory path.
	 *
	 * @return the token directory path
	 */
	public final String getTokenDirectoryPath() {
		return tokenDirectoryPath;
	}

	/**
	 * Gets the credentials file path.
	 *
	 * @return the credentials file path
	 */
	public final String getCredentialsFilePath() {
		return credentialFilePath;
	}

	/**
	 * Gets the o auth 2 callback url.
	 *
	 * @return the o auth 2 callback url
	 */
	public String getoAuth2CallbackUrl() {
		return "/oAuth2Callback";
	}
}
