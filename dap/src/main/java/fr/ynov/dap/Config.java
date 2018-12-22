package fr.ynov.dap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Gère la configuration sur le principe "zero conf", c'est à dire qu'une conf de base 
 * est initialisée, mais via les setter un developpeur/admin peut facilement changer
 * les différentes variables.
 * 
 * @author abaracas
 *
 */
//TODO baa by Djer |Design Patern| Externalisation de la configuration (pouvoir modifier les paramètre en ligne de commande)
public class Config {
    private String applicationName;
    private FileInputStream clientSecretFile;
    private String credentialFolder;
    private String url;
    /**
     * Construit la config de base
     */
    public Config() {
//	setApplicationName("Ynov DAP");
//	setClientSecretFile("tokens");
//	setCredentialFolder("/credentials.json");
//	setUrl("/oAuth2Callback");
// TODO MR DERUETTE : IMPORTANT pour le devoir 3 : Mon pc étant à mon entreprise, l'externalisation du credential entraîne un "accès refusé", je ne peux donc pas pleinement le tester.
	//Si jamais vous rencontrez un problème, je vous invite à commenter les 8 prochaines lignes, décommenter les 4 qui suivre Config(), et remettre le type de clientSecretFile à String.
		setApplicationName("Ynov DAP");
		//TODO baa by Djer |Design Patern| Tu as un accès refusé car il n'y a pas d'extension et que Java cherche un "dossier" "credentials" (je t'ai modifier en ajoutant le .json (je met aussi dans un sous dossier "dap" directement à la racine du "home" ces fichiers ne sont pas des "documents")
		//TODO baa by Djer |Design Patern| Utilise "fileseparator" pour etre compatible window/linux
		setCredentialFolder(System.getProperty("user.home") +  "\\dap\\baa\\credentials.json"); 
		try {
		  //TODO baa by Djer |API Google| Tu as inversé "secretFile" et "FolderDir", mais il s'agit de bien de 2 choses différeentes
		    //TODO baa by Djer |POO| Dans ton GoogleService, c'est une chaine de texte qui est attedu (et dans ta config il faut mieux laisser deschaine de texte, ca permetra de "facilement" les modifier de l'exterieur)
		    //setClientSecretFile(new FileInputStream(new File(getCredentialFolder()))) ;
		    setClientSecretFile(new FileInputStream(new File(System.getProperty("user.home") +  "\\dap\\baa\\tokens"))) ;
		} catch (FileNotFoundException e) {
		  //TODO baa by Djer |Log4J| Une petite log ?
		    e.printStackTrace();
		}		
		setUrl("/oAuth2Callback");
    }
    /**
     * @return the applicationName
     */
    public String getApplicationName() {
	return applicationName;
    }
    /**
     * @param applicationName the applicationName to set
     */
    public void setApplicationName(String applicationName) {
	this.applicationName = applicationName;
    }
    /**
     * @return the clientSecretFile
     */
    public FileInputStream getClientSecretFile() {
	return clientSecretFile;
    }
    /**
     * @param clientSecretFile the clientSecretFile to set
     */
    public void setClientSecretFile(FileInputStream clientSecretFile) {
	this.clientSecretFile = clientSecretFile;
    }
    /**
     * @return the credentialFolder
     */
    public String getCredentialFolder() {
	return credentialFolder;
    }
    /**
     * @param credentialFolder the credentialFolder to set
     */
    public void setCredentialFolder(String credentialFolder) {
	this.credentialFolder = credentialFolder;
    }
    /**
     * @return the url
     */
    public String getUrl() {
	return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
	this.url = url;
    }

}
