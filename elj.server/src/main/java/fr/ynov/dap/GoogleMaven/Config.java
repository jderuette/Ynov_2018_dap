package fr.ynov.dap.GoogleMaven;


//TODO elj by Djer Configure tes "save Actions" dans Elcipse. Cf mémo elcipse
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public class Config {
	private static final String CREDENTIALS_FILE = "/credentials.json";
	private static final String CLIENT_SECRET_DIR = "tokens";
	private static final String APPLICATION_NAME = "HoCDaP";
	public String applicationName;
	public String credentialFolder;
	public String clientSecretFile;
	
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

	//TODO elj by Djer Devrait ausis etrene mode "ZeroConf"
	public String getoAuth2CallbackUrl() {
		// TODO Auto-generated method stub
		return "/oAuth2Callback";
	}

	//TODO elj by Djer Cela ressemble à un "doublon" de "getCredentialFolder()"
	public String getTokensDirectoryPath() {
		// TODO Auto-generated method stub
		return "tokens";
	}
	
}