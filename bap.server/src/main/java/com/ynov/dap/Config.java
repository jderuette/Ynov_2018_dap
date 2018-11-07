package com.ynov.dap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource("classpath:config.properties")
public class Config {

	@Autowired
	private Environment env;
	
	public Config() {}

    private String oAuth2CallbackUrl = env.getProperty("oAuth2Callback");

    private String clientSecretFile = env.getProperty("credentials_file");

    private String applicationName = env.getProperty("application_name");

    private String credentialsFolder = env.getProperty("credentials_tokens");

    public String getoAuth2CallbackUrl() {
        return oAuth2CallbackUrl;
    }

    private String dataStoreDirectory = System.getProperty("user.home");

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

    public String getClientSecretFile() {
        return clientSecretFile;
    }

    public String getApplicationName() {
        return applicationName;
    }
	
}
