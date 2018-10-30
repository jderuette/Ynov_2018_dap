
package fr.ynov.dap;

public class Config {

    //TODO dea by Djer Pense à ranger ton code : CONSTANTES, puis attributs, puis Constructeur, puis méthode métier, puis getter/setters

    private static final String CREDENTIALS_FOLDER = "credentials";
    private static final String CLIENT_SECRET_DIR = "tokens";
    private static final String APPLICATION_NAME = "HoC DaP";

    /**
     * Récupère le nom de l'application
     * 
     * @return
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Change le nom de l'application
     * 
     * @param applicationName
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * Récupère le chemin du fichier où est rangé le fichier credential.json
     * 
     * @return
     */
    public String getCredentialFolder() {
        return applicationFolder + System.getProperty("file.separator") + credentialFolder;
    }

    /**
     * Change le chemin du fichier dans lequel est rangé le fichier credential.json
     * 
     * @param credentialFolder
     */
    public void setCredentialFolder(String credentialFolder) {
        this.credentialFolder = credentialFolder;
    }

    /**
     * Récupère le chemin du répertoire tokens
     * 
     * @return
     */
    public String getClientSecretFile() {
        return applicationFolder + System.getProperty("file.separator") + clientSecretFile;
    }

    /**
     * Change le chemin où est rangé le répertoire tokens
     * 
     * @param clientSecretFile
     */
    public void setClientSecretFile(String clientSecretFile) {
        this.clientSecretFile = clientSecretFile;
    }

    /**
     * Change le chemin de la racine du répertoire du projet
     * 
     * @param path
     */
    public void setRacineFolder(String path) {
        this.applicationFolder = path;
    }

    //TODO dea by Djer commentaire pas vraiment "juste", 
    //ca serait plutot le dossier de "sauvegarde" et le dossier des "données"
    /**
     * Récupère le chemin de la racine du répertoire du projet
     * 
     * @return
     */
    public String getRacineFolder() {
        return applicationFolder;
    }

    private String applicationName;
    private String credentialFolder;
    private String clientSecretFile;
    private String applicationFolder;

    /**
     * Constructeur de la classe de configuration.
     * 
     */
    public Config() {
        //TODO dea by Djer on essaye de mettre les "valeur par defaut" dans des constantes, vers le haut de la classe.
        //Cela facilite la lisibilité du code.
        applicationFolder = System.getProperty("user.home") + System.getProperty("file.separator");
        applicationName = APPLICATION_NAME;
        credentialFolder = CREDENTIALS_FOLDER;
        clientSecretFile = CLIENT_SECRET_DIR;
    }

    /**
     * Renvoie l'url de redirection
     * 
     * @return
     */
    public String getoAuth2CallbackUrl() {
        return "/oAuth2Callback";
    }
}
