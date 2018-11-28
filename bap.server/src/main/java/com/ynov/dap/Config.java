package com.ynov.dap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config.properties")
@Configuration
public class Config {
	
    private String dataStoreDirectory = System.getProperty("user.home");
	
    @Value("${oAuth2CallbackUrl}")
    private String oAuth2CallbackUrl = "/oAuth2Callback";

    @Value("${credentials_file}")
    private String clientSecretFile = "credentials.json";

    @Value("${application_name}")
    private String applicationName = "HoCDaP";

    @Value("${credentials_folder}")
    private String credentialsFolder = dataStoreDirectory + "/google/credential";
    
    @Value("${credentials_tokens}")
    private String credentialsFolderToken = dataStoreDirectory + "/google/tokens";
    
	public Config() {}
    
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
