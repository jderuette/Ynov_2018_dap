package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookUser {
    private String id;
    private String mail;
    private String displayName;

    public final String getId() {
        return id;
    }
    public final void setId(String id) {
        this.id = id;
    }
    public final String getMail() {
        return mail;
    }
    public final void setMail(String emailAddress) {
        this.mail = emailAddress;
    }
    public final String getDisplayName() {
        return displayName;
    }
    public final void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
