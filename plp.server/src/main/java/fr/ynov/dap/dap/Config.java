package fr.ynov.dap.dap;

import java.io.File;

/**
 * Config of the application.
 */
public class Config {
    /**
     * credentials folder for zero config.
     */
    private static final String CREDENTIALS_FOLDER = "/google/credentials.json";
    /**
     * Folder for secret client for zero config.
     */
    private static final String CLIENT_SECRET_DIR = "tokens";
    /**
     * The application name.
     */
    private static final String APPLICATION_NAME = "Ynov DaP";

    /**
     * public application name.
     */
    //TODO plp by Djer Checkstyle te signale que ca devrait être privé avec acesseurs.
    public String applicationName;
    /**
     * Public config for credential folder.
     */
    //TODO plp by Djer Checkstyle te signale que ca devrait être privé avec acesseurs.
    public String credentialFolder;
    /**
     * Public config for secret client folder.
     */
    //TODO plp by Djer Checkstyle te signale que ca devrait être privé avec acesseurs.
    public String clientSecretFile;

    /**
     * Initialize public variable.
     */
    public Config() {
        applicationName = APPLICATION_NAME;
        credentialFolder = CREDENTIALS_FOLDER;
        clientSecretFile = CLIENT_SECRET_DIR;
    }

    /**
     * set the path of credential Folder for personal config.
     *
     * @param credentialFolder : path to credential folder
     */
    public final void setCredentialsFolder(final String credentialFolder) {
        if (File.separatorChar != '/') {
            this.credentialFolder = credentialFolder.replace('/', File.separatorChar);
        } else {
            this.credentialFolder = credentialFolder.replace('\\', File.separatorChar);
        }
    }

    /**
     * set the path file to secret client file for personal config.
     *
     * @param clientSecretFile : path to secret client file
     */
    public final void setClientSecretFile(final String clientSecretFile) {
        //TODO plp by Djer Etrange, tu devrait plutot tester si la chaine "contient \".
        // File.separator te renvoie le séparator du systeme ou s'éxécute Java (le server ici).
        if (File.separatorChar != '/') {
            this.clientSecretFile = clientSecretFile.replace('/', File.separatorChar);
        } else {
            this.clientSecretFile = clientSecretFile.replace('\\', File.separatorChar);
        }
    }

    /**
     * URl for Authenticate Call back.
     *
     * @return String : URL too callback
     */
    final String getoAuth2CallbackUrl() {
        return "/oAuth2Callback";
    }

}
