package fr.ynov.dap.dap.model;

/**
 *
 * @author David_tepoche
 *
 */
public class MicrosoftUserDetail {

    /**
     * the display name.
     */
    private String displayName;
    /**
     * the surname.
     */
    private String surname;
    /**
     * given name.
     */
    private String givenName;
    /**
     * id.
     */
    private String id;
    /**
     * user principal name.
     */
    private String userPrincipalName;
    /**
     * the mail.
     */
    private String mail;

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(final String surname) {
        this.surname = surname;
    }

    /**
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @param givenName the givenName to set
     */
    public void setGivenName(final String givenName) {
        this.givenName = givenName;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @return the userPrincipalName
     */
    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    /**
     * @param userPrincipalName the userPrincipalName to set
     */
    public void setUserPrincipalName(final String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(final String mail) {
        this.mail = mail;
    }
}
