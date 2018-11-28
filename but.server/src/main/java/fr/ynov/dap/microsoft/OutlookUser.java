package fr.ynov.dap.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Outlook user entity.
 * @author thibault
 *
 */
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
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param idToSet the id to set
     */
    public void setId(final String idToSet) {
        this.id = idToSet;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mailToSet the mail to set
     */
    public void setMail(final String mailToSet) {
        this.mail = mailToSet;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayNameToSet the displayName to set
     */
    public void setDisplayName(final String displayNameToSet) {
        this.displayName = displayNameToSet;
    }
}
