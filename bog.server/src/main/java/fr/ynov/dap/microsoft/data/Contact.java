package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Mon_PC
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
    /**.
     * Propriété id
     */
    private String id;
    /**.
     * Propriété givenName
     */
    private String givenName;
    /**.
     * Propriété surname
     */
    private String surname;
    /**.
     * Propriété companyName
     */
    private String companyName;
    /**.
     * Propriété emailAddresses
     */
    private EmailAddress[] emailAddresses;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**.
     * @param newId correspondant au nouveau id
     */
    public void setId(final String newId) {
        this.id = newId;
    }

    /**
     * @return givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**.
     * @param newGivenName correspondant au nouveau givenName
     */
    public void setGivenName(final String newGivenName) {
        this.givenName = newGivenName;
    }

    /**
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**.
     * @param newSurname correspondant au nouveau surname
     */
    public void setSurname(final String newSurname) {
        this.surname = newSurname;
    }

    /**
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**.
     * @param newCompanyName correspondant au nouveau companyName
     */
    public void setCompanyName(final String newCompanyName) {
        this.companyName = newCompanyName;
    }

    /**
     * @return emailAddresses
     */
    public EmailAddress[] getEmailAddresses() {
        return emailAddresses;
    }

    /**.
     * @param newEmailAddresses correspondant au nouveau emailAddresses
     */
    public void setEmailAddresses(final EmailAddress[] newEmailAddresses) {
        this.emailAddresses = newEmailAddresses;
    }
}
