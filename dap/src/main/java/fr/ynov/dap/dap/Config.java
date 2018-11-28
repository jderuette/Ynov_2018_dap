package fr.ynov.dap.dap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author David_tepoche
 *
 */
@Configuration
@PropertySource("classpath:config.properties")
public class Config {
    /**
     * store the oAuth2CallbackUrl.
     */
    @Value("${google.root.url.redirect}")
    private String googleRootUrlCallBack;
    /**
     * store the oAuth2CallbackUrl.
     */
    @Value("${microsoft.root.url.redirect}")
    private String microsoftUrlCallBack;

    /**
     * the authority url for the first connection.
     */
    @Value("${microsoft.authority.url}")
    private String microsoftAuthorityUrl;
    /**
    *
    */
    @Value("${google.secret.file.name}")
    private String googleClientSecretFile;
    /**
    *
    */
    @Value("${microsoft.secret.file.name}")
    private String microsoftClientSecretFile;
    /**
    *
    */
    @Value("${application.name}")
    private String applicationName;
    /**
    *
    */
    @Value("${google.credential.folder.name}")
    private String credentialsFolder;
    /**
     * get the directory of the dataStore default = the home directory of the user.
     */
    private String dataStoreDirectory = System.getProperty("user.home");

    /**
    *
    */
    public Config() {
    }

    /**
     * @return the microsoftAuthorityUrl
     */
    public String getMicrosoftAuthorityUrl() {
        return microsoftAuthorityUrl;
    }

    /**
     * @param msAuthorityUrl the microsoftAuthorityUrl to set
     */
    public void setMicrosoftAuthorityUrl(final String msAuthorityUrl) {
        this.microsoftAuthorityUrl = msAuthorityUrl;
    }

    /**
     * @return the oAuth2CallbackUrl
     */
    public String getoAuth2CallbackUrl() {
        return googleRootUrlCallBack;
    }

    /**
     *
     * @param dataDirectory the path of the new directory to store the credential
     */
    public Config(final String dataDirectory) {
        this.dataStoreDirectory = dataDirectory;
    }

    /**
     * @return the dataStoreDirectory
     */
    public String getDataStoreDirectory() {
        return this.dataStoreDirectory;
    }

    /**
     * @return the credentialsFolder
     */
    public String getCredentialsFolder() {
        return credentialsFolder;
    }

    /**
     * @return the microsoftClientSecretFile
     */
    public String getMicrosoftClientSecretFile() {
        return microsoftClientSecretFile;
    }

    /**
     * @return the microsoftRootUrlCallBack
     */
    public String getMicrosoftRootUrlCallBack() {
        return microsoftUrlCallBack;
    }

    /**
     * @return the clientSecretDir
     */
    public String getGoogleClientSecretFile() {
        return this.googleClientSecretFile;
    }

    /**
     * @return the applicationName
     */
    public String getApplicationName() {
        return this.applicationName;
    }

}
