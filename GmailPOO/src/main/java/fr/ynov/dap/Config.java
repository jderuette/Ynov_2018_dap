package fr.ynov.dap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

public class Config {
    private final String CREDENTIALS_FILE = "/credentials.json";
    private final String CLIENT_SECRET_DIR = "tokens";
    private final String APPLICATION_NAME = "HoCDaP";
    private final String SCOOPLIST[] = { GmailScopes.GMAIL_READONLY, CalendarScopes.CALENDAR,
            PeopleServiceScopes.CONTACTS_READONLY };

    private java.util.List<String> allSCOPES;
    public String applicationName;
    public String credentialFolder;
    public String clientSecretFile;

    public Config() {
        this.applicationName = APPLICATION_NAME;
        this.clientSecretFile = CREDENTIALS_FILE;
        this.credentialFolder = CLIENT_SECRET_DIR;
        this.allSCOPES = new ArrayList<String>(Arrays.asList(SCOOPLIST));

    }

    /**
     * @return the allSCOPES
     */
    public java.util.List<String> getAllSCOPES() {
        return allSCOPES;
    }

    /**
     * @param newAllSCOPES the allSCOPES to set
     */
    public void setAllSCOPES(final List<String> newAllSCOPES) {
        this.allSCOPES = newAllSCOPES;
    }

    /**
     * @return the applicationName
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * @param newApplicationName the applicationName to set
     */
    public void setApplicationName(final String newApplicationName) {
        this.applicationName = newApplicationName;
    }

    /**
     * @return the credentialFolder
     */
    public String getCredentialFolder() {
        return credentialFolder;
    }

    /**
     * @param newCredentialFolder the credentialFolder to set
     */
    public void setCredentialFolder(final String newCredentialFolder) {
        this.credentialFolder = newCredentialFolder;
    }

    /**
     * @return the clientSecretFile
     */
    public String getClientSecretFile() {
        return clientSecretFile;
    }

    /**
     * @param newClientSecretFile the clientSecretFile to set
     */
    public void setClientSecretFile(final String newClientSecretFile) {
        this.clientSecretFile = newClientSecretFile;
    }

    public String getoAuth2CallbackUrl() {

        return "http://localhost:8080/Callback";
    }

    public String getMapping() {

        return "/Callback";
    }

}