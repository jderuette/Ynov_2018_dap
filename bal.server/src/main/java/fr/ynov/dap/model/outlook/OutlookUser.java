package fr.ynov.dap.model.outlook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookUser {

    private String id;

    private String mail;

    private String displayName;

    public String getId() {
        return id;
    }

    public void setId(final String val) {
        this.id = val;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(final String val) {
        this.mail = val;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String val) {
        this.displayName = val;
    }
}
