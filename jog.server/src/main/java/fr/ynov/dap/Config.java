package fr.ynov.dap;

public class Config {

    //TODO job by Djer |Audit Code| Configure tes outils d'audit de code ! Les attributs commencent par une majusucule
	private String CredentialFolder = System.getProperty("user.home") + System.getProperty("file.separator") + "dap"
			+ System.getProperty("file.separator") + "projet_credentials.json";
	private String ClientSecretFile = "tokens";
	private String ApplicationName = "Gmail API Java Quickstart";
	private String CallBackUrl = "/oAuth2Callback";
	
	//TODO job by Djer |Design Patern| Externaliser la conf ? 

	public Config() {
	    //TODO jog by Djer |Design Patern| Pas utile de setter un attribut avec sa propre valeur ....
		setApplicationName(ApplicationName);
		setClientSecretFile(ClientSecretFile);
		setCredentialFolder(CredentialFolder);
		setCallBackUrl(CallBackUrl);
	}

	public String getCredentialFolder() {
		return CredentialFolder;
	}

	public void setCredentialFolder(String credentialFolder) {
		CredentialFolder = credentialFolder;
	}

	public String getClientSecretFile() {
		return ClientSecretFile;
	}

	public void setClientSecretFile(String clientSecretFile) {
		ClientSecretFile = clientSecretFile;
	}

	public String getApplicationName() {
		return ApplicationName;
	}

	public void setApplicationName(String applicationName) {
		ApplicationName = applicationName;
	}

	public void setCallBackUrl(String callBackUrl) {
		CallBackUrl = callBackUrl;
	}

	public String getoAuth2CallbackUrl() {
	    //TODO job by Djer |IDE| Suprimme les TO-DO lorsqu'ils sont traités
		// TODO Auto-generated method stub

		return CallBackUrl;
	}

}