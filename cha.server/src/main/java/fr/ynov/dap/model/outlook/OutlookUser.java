package fr.ynov.dap.model.outlook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookUser {

    /**
     * Id.
     */
    private String id;

    /**
     * Mail.
     */
    private String mail;

    /**
     * Display name.
     */
    private String displayName;

    /**
     * Return user id.
     * @return user id
     */
    public String getId() {
        return id;
    }

    /**
     * Set user id.
     * @param val user
     */
    public void setId(final String val) {
        this.id = val;
    }

    /**
     * Return user's mail.
     * @return Mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Set user email.
     * @param val mail
     */
    public void setMail(final String val) {
        this.mail = val;
    }

    /**
     * Return display name.
     * @return Display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set display name.
     * @param val Display name.
     */
    public void setDisplayName(final String val) {
        this.displayName = val;
    }
}
