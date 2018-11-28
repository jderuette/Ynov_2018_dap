package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Mon_PC
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookUser {
    /**.
     * propriété id
     */
    private String id;
    /**.
     * propriété mail
     */
    private String mail;
    /**.
     * propriété displayName
     */
    private String displayName;

    /**
     * @return identifiant
     */
    public String getId() {
        return id;
    }

    /**.
     * Set new Id
     * @param newId correspondant au nouveau Id
     */
    public void setId(final String newId) {
        this.id = newId;
    }

    /**
     * @return mail
     */
    public String getMail() {
        return mail;
    }

    /**.
     * Set new mail
     * @param newEmailAddress correspondant à la nouvelle adresse mail
     */
    public void setMail(final String newEmailAddress) {
        this.mail = newEmailAddress;
    }

    /**
     * @return displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**.
     * Set new displayName
     * @param newDisplayName correspondant au nouveau displayName
     */
    public void setDisplayName(final String newDisplayName) {
        this.displayName = newDisplayName;
    }
}
