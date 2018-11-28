package fr.ynov.dap.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entity Contact.
 * @author thibault
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
    /**
     * Unique ID.
     */
    private String id;
    /**
     * Givend name.
     */
    private String givenName;
    /**
     * Surname.
     */
    private String surname;
    /**
     * Company name.
     */
    private String companyName;
    /**
     * Email addresses.
     */
    private EmailAddress[] emailAddresses;
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
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }
    /**
     * @param givenNameToSet the givenName to set
     */
    public void setGivenName(final String givenNameToSet) {
        this.givenName = givenNameToSet;
    }
    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }
    /**
     * @param surnameToSet the surname to set
     */
    public void setSurname(final String surnameToSet) {
        this.surname = surnameToSet;
    }
    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * @param companyNameToSet the companyName to set
     */
    public void setCompanyName(final String companyNameToSet) {
        this.companyName = companyNameToSet;
    }
    /**
     * @return the emailAddresses
     */
    public EmailAddress[] getEmailAddresses() {
        return emailAddresses;
    }
    /**
     * @param emailAddressesToSet the emailAddresses to set
     */
    public void setEmailAddresses(final EmailAddress[] emailAddressesToSet) {
        this.emailAddresses = emailAddressesToSet;
    }
}
