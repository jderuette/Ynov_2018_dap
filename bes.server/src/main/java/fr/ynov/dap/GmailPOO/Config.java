package fr.ynov.dap.GmailPOO;


import org.springframework.beans.factory.annotation.Autowired;
//TODO bes by Djer Configure les "save action" de ton IDE. Cf Mémo Eclipse.
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public class Config {
	private static final String CREDENTIALS_FILE = "/credentials.json";
	private static final String CLIENT_SECRET_DIR = "tokens";
	private static final String APPLICATION_NAME = "HoCDaP";
	public String applicationName;
	public String credentialFolder;
	public String clientSecretFile;
	//TODO bes by Djer Je ne suis pas sure qu'un @Autowire" sur un constrcuteur fonctionne correctement
	@Autowired
	 public Config (){
           this.applicationName=APPLICATION_NAME;
           this.clientSecretFile=CREDENTIALS_FILE;
           this.credentialFolder=CLIENT_SECRET_DIR;
           
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
	 * @return the clientSecretFile
	 */
	public String getClientSecretFile() {
		return clientSecretFile;
	}

	/**
	 * @param clientSecretFile the clientSecretFile to set
	 */
	public void setClientSecretFile(String clientSecretFile) {
		this.clientSecretFile = clientSecretFile;
	}

	public String getoAuth2CallbackUrl() {
	    //TODO bes by DJer Ce TODO ne semble plus juste. A supprimer (ou à traiter)
		// TODO Auto-generated method stub
	
		return "http://localhost:8080/Callback";
	}
	
}