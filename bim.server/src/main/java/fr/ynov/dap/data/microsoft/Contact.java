package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Microsoft contact.
 * @author MBILLEMAZ
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
    /**
     * id.
     */
    private String id;
    /**
     * name.
     */
    private String givenName;
    /**
     * surname.
     */
    private String surname;
    /**
     * Company name.
     */
    private String companyName;

    /**
     * List of contact mail addresses.
     */
    private EmailAddress[] emailAddresses;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
    public void setGivenName(String givenName) {
        this.givenName = givenName;
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
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the emailAddresses
     */
    public EmailAddress[] getEmailAddresses() {
        return emailAddresses;
    }

    /**
     * @param emailAddresses the emailAddresses to set
     */
    public void setEmailAddresses(EmailAddress[] emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

}
