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
     * TODO duv by Djer |JavaDoc| Dire à quoi ca sert, plutot que ce que c'est (déja assez bien indiqué par le nom de la variable).
     * 
     * Exemple : je suis Jérémie, mais aussi "Monsieur" (dans les salle de cours c'est comme cela qu'on m'appel). 
     * Mon nom évolue en fonction du context (comme un paramète de méthode qui n'a pas exactement le nom de la variable dans lequel il sera injecté).
     * Je sert à : ensiegner le Java dans le cadre de ynov Campus. Et à pliens d'autre choses "hors sujet".
     * 
     * Si tu me croise en salle de cour et que tu me demande "Que fais-tu là ?" et que je te répond "Jérémie ! " 
     * c'est aussi utile que si je t'avais répondu "je s'appel Root".
     * 
     * 
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
    //TODO duv by Djer |JavaDoc| Prendre en compte le warning indiquant qu'il manque la Javadoc ? 
    /**
    * Djer : mais a quoi ca peut bien servir ? 
    */
    @Value("${google.secret.file.name}")
    private String googleClientSecretFile;
    //TODO duv by Djer |JavaDoc| Prendre en compte le warning indiquant qu'il manque la Javadoc ? 
    /**
    *
    */
    @Value("${microsoft.secret.file.name}")
    private String microsoftClientSecretFile;
    //TODO duv by Djer |JavaDoc| Prendre en compte le warning indiquant qu'il manque la Javadoc ? 
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
    //TODO duv by Djer |Design Patern| Mince ! J'aurais bien aimé chioisr un sous dossier dans mon "user home". Pourquoi cet attribut n'est pas mappé sur une entrée du fichier de config ?
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
