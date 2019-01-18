package fr.ynov.dap.outlookService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookUser {
    private String id;
    private String mail;
    private String displayName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(final String newEmailAddress) {
        this.mail = newEmailAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String newDisplayName) {
        this.displayName = newDisplayName;
    }
}
