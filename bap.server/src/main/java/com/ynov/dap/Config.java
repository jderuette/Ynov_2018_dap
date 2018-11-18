package com.ynov.dap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource("classpath:config.properties")
public class Config {

	@Autowired
	private Environment env;
	
    private String dataStoreDirectory = System.getProperty("user.home");
	
    private String oAuth2CallbackUrl = "/oAuth2Callback";

    private String clientSecretFile = "credentials.json";

    private String applicationName = "HoCDaP";

    private String credentialsFolder = dataStoreDirectory + "/google/credential";
    private String credentialsFolderToken = dataStoreDirectory + "/google/tokens";
    
	public Config() {
		System.out.println(env);
		
		/*
		if (env.getProperty("application_name") != null) {
			this.applicationName = env.getProperty("application_name");
		}
		
		if (env.getProperty("credentials_file") != null) {
			this.clientSecretFile = env.getProperty("credentials_file");
		}
		
		if (env.getProperty("credentials_folder") != null) {
			this.credentialsFolder = env.getProperty("credentials_folder");
		}
		
		if (env.getProperty("credentials_token") != null) {
			this.credentialsFolderToken = env.getProperty("credentials_token");
		}
		
		if (env.getProperty("oAuth2CallbackUrl") != null) {
			this.oAuth2CallbackUrl = env.getProperty("oAuth2CallbackUrl");
		}
		*/
	}
    
    public String getoAuth2CallbackUrl() {
        return oAuth2CallbackUrl;
    }

    public Config(final String dataDirectory) {
        this.dataStoreDirectory = dataDirectory;
    }

    public String getDataStoreDirectory() {
        return this.dataStoreDirectory;
    }

    public void setDataStoreDirectory(final String dataDirectory) {
        this.dataStoreDirectory = dataDirectory;
    }

    public String getCredentialsFolder() {
        return credentialsFolder;
    }
    
    public String getCredentialsFolderToken() {
        return credentialsFolderToken;
    }

    public String getClientSecretFile() {
        return clientSecretFile;
    }

    public String getApplicationName() {
        return applicationName;
    }
	
}
