package fr.ynov.dap;


//TODO job by Djer Javadoc ! 
public class Config {
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    
    private String CredentialFolder = CREDENTIALS_FILE_PATH;
    private String ClientSecretFile = TOKENS_DIRECTORY_PATH;
    //TODO jog by Djer PAS de "underscore" dans les nom d'attributs ! 
    private String application_name = APPLICATION_NAME;
    
    
    public Config() {
	    setApplication_name(APPLICATION_NAME);
		setClientSecretFile(TOKENS_DIRECTORY_PATH);
		setCredentialFolder(CREDENTIALS_FILE_PATH);
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
	public String getApplication_name() {
		return application_name;
	}
	public void setApplication_name(String application_name) {
		application_name = application_name;
	}
	
	//TODO jog by Djer Devrait être ne mode "ZroConf" aussi.
	public String getoAuth2CallbackUrl() {
	    //TODO jog by Djer Ce TOD ne semble plus vrai !
		// TODO Auto-generated method stub
	
		return "http://localhost:8080/Callback";
	}
    

    
	
}