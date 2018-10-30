package fr.ynov.dap.dap;

/**
 * Classe de configuration.
 * @author alex
 */
public class Config {
    // TODO roa by Djer Cela fonctionne, mettre en constante les "chaine de texte"
    // serait plus propore. Cela impliquera de "pré-remplire" les attribut dans le constructeur
    /**
     * Chemin de récupération des credentials pour la connexion à google.
     */
    private final String credentialsFilePath = "/credentials.json";
    /**
     * Chemin de stockage des tokens de connexion à google incluant les droits
     * d'accès aux différentes API google.
     */
    private final String tokensDirectionPath = "tokens";
    /**
     * Nom de l'application.
     */
    private final String applicationName = "Ynov dap";

    /**
     * Permet de récupérer le nom de l'application.
     * @return String
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Permet de récupérer le chemin des crédentials.
     * @return String
     */
    public String getCredentialFilePath() {
        return credentialsFilePath;
    }

    /**
     * Permet de récupérer le chemin des tokens.
     * @return String
     */
    public String getClientSecretFile() {
        return tokensDirectionPath;
    }

    /**
     * Renvois l'url de callBack de oAuth2.
     * @return String
     */
    public String getoAuth2CallbackUrl() {
        return "/oAuth2Callback";
    }
}
