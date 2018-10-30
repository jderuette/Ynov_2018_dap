package fr.ynov.dap;

public class Config {

    private static final String CREDENTIAL_FOLDERS_DEFAULT = "/credentials.json";
    private static final String CLIENT_SECRET_DIR_DEFAULT = "tokens";
    private static final String APPLICATION_NAME_DEFAULT = "Ynov DAP";
    private String applicationName;
    private String clientSecretFile;
    private String credentialFolder;

    public Config() {
	setApplicationName(APPLICATION_NAME_DEFAULT);
	setClientSecretFile(CLIENT_SECRET_DIR_DEFAULT);
	setCredentialFolder(CREDENTIAL_FOLDERS_DEFAULT);
    }
    public String getApplicationName() {
	return applicationName;
    }

    public void setApplicationName(String applicationName) {
	this.applicationName = applicationName;
    }

    public String getClientSecretFile() {
	return clientSecretFile;
    }

    public void setClientSecretFile(String clientSecretFile) {
	this.clientSecretFile = clientSecretFile;
    }

    public String getCredentialFolder() {
	return credentialFolder;
    }

    public void setCredentialFolder(String credentialFolder) {
	this.credentialFolder = credentialFolder;
    }
    public String getoAuth2CallbackUrl() {
	return "/getoAuth2CallbackUrl";
    }
}
