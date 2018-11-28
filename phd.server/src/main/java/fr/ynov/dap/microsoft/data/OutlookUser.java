package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * .
 * @author Dom .
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookUser {
    /**
     * .
     */
    private String id;
    /**
     * .
     */
    private String mail;
    /**
     * .
     */
    private String displayName;

    /**
     *
     * @return .
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param mId .
     */
    public void setId(final String mId) {
        this.id = mId;
    }

    /**
     *
     * @return .
     */
    public String getMail() {
        return mail;
    }

    /**
     *
     * @param emailAddress .
     */
    public void setMail(final String emailAddress) {
        this.mail = emailAddress;
    }

    /**
     *
     * @return .
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param mDisplayName .
     */
    public void setDisplayName(final String mDisplayName) {
        this.displayName = mDisplayName;
    }
}
