package fr.ynov.dap.dap.microsoft.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Florian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
    /**.
     * Déclaration de l'id
     */
    private String id;
    /**.
     * Déclaration de givenName
     */
    private String givenName;
    /**.
     * Déclaration de surname
     */
    private String surname;
    /**.
     * Déclaration de companyName
     */
    private String companyName;
    /**.
     * Déclaration de emailAddresses
     */
    private EmailAddress[] emailAddresses;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param theId Modification de la valeur
     */
    public void setId(final String theId) {
        this.id = theId;
    }

    /**
     * @return givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @param theGivenName Modification de la valeur
     */
    public void setGivenName(final String theGivenName) {
        this.givenName = theGivenName;
    }

    /**
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param theSurname Modification de la valeur
     */
    public void setSurname(final String theSurname) {
        this.surname = theSurname;
    }

    /**
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param theCompanyName Modification de la valeur
     */
    public void setCompanyName(final String theCompanyName) {
        this.companyName = theCompanyName;
    }

    /**
     * @return emailAddresses
     */
    public EmailAddress[] getEmailAddresses() {
        return emailAddresses;
    }

    /**
     * @param theEmailAddresses Modification de la valeur
     */
    public void setEmailAddresses(final EmailAddress[] theEmailAddresses) {
        this.emailAddresses = theEmailAddresses;
    }
}
