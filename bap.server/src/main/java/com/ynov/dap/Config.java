package com.ynov.dap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class Config.
 */
@PropertySource("classpath:config.properties")
@Configuration
public class Config {
	
    /** The data store directory. */
    private String dataStoreDirectory = System.getProperty("user.home");
	
    /** The o auth 2 callback url. */
    @Value("${oAuth2CallbackUrl}")
    private String oAuth2CallbackUrl = "/oAuth2Callback";

    /** The client secret file. */
    @Value("${credentials_file}")
    private String clientSecretFile = "credentials.json";

    /** The application name. */
    @Value("${application_name}")
    private String applicationName = "HoCDaP";

    /** The credentials folder. */
    @Value("${credentials_folder}")
    private String credentialsFolder = dataStoreDirectory + "/google/credential";
    
    /** The credentials folder token. */
    @Value("${credentials_tokens}")
    private String credentialsFolderToken = dataStoreDirectory + "/google/tokens";
    
    /** The authority. */
    @Value("${microsoft_authority}")
    private String authority = "https://login.microsoftonline.com";
    
	/** The authorize url. */
	@Value("${microsoft_authorize_url}")
    private String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";
    
    
	/**
	 * Instantiates a new config.
	 */
	public Config() {}
    
    /**
     * Gets the o auth 2 callback url.
     *
     * @return the o auth 2 callback url
     */
    public String getoAuth2CallbackUrl() {
        return oAuth2CallbackUrl;
    }

    /**
     * Instantiates a new config.
     *
     * @param dataDirectory the data directory
     */
    public Config(final String dataDirectory) {
        this.dataStoreDirectory = dataDirectory;
    }

    /**
     * Gets the data store directory.
     *
     * @return the data store directory
     */
    public String getDataStoreDirectory() {
        return this.dataStoreDirectory;
    }

    /**
     * Sets the data store directory.
     *
     * @param dataDirectory the new data store directory
     */
    public void setDataStoreDirectory(final String dataDirectory) {
        this.dataStoreDirectory = dataDirectory;
    }

    /**
     * Gets the credentials folder.
     *
     * @return the credentials folder
     */
    public String getCredentialsFolder() {
        return credentialsFolder;
    }
    
    /**
     * Gets the credentials folder token.
     *
     * @return the credentials folder token
     */
    public String getCredentialsFolderToken() {
        return credentialsFolderToken;
    }

    /**
     * Gets the client secret file.
     *
     * @return the client secret file
     */
    public String getClientSecretFile() {
        return clientSecretFile;
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
     * Gets the authority.
     *
     * @return the authority
     */
    public String getAuthority() {
		return authority;
	}

	/**
	 * Sets the authority.
	 *
	 * @param authority the new authority
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * Gets the authorize url.
	 *
	 * @return the authorize url
	 */
	public String getAuthorizeUrl() {
		return authorizeUrl;
	}

	/**
	 * Sets the authorize url.
	 *
	 * @param authorizeUrl the new authorize url
	 */
	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}

}
