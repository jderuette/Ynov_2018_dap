package fr.ynov.dap.services.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Contact entity used by the Microsoft API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
    /**
     * Id.
     */
    private String id;
    /**
     * Given name.
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
     * Email Address array.
     */
    private EmailAddress[] emailAddresses;

    /**
     * Id getter.
     * @return Id.
     */
    public String getId() {
        return id;
    }
    /**
     * Id setter.
     * @param i id.
     */
    public void setId(final String i) {
        this.id = i;
    }
    /**
     * GivenName getter.
     * @return given name.
     */
    public String getGivenName() {
        return givenName;
    }
    /**
     * GivenName setter.
     * @param given given name.
     */
    public void setGivenName(final String given) {
        this.givenName = given;
    }
    /**
     * Surname getter.
     * @return surname.
     */
    public String getSurname() {
        return surname;
    }
    /**
     * Surname setter.
     * @param surn surname.
     */
    public void setSurname(final String surn) {
        this.surname = surn;
    }
    /**
     * CopanyName getter.
     * @return company name.
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * CompanyName setter.
     * @param company company name.
     */
    public void setCompanyName(final String company) {
        this.companyName = company;
    }
    /**
     * EmailAddress getter.
     * @return Email addresses array.
     */
    public EmailAddress[] getEmailAddresses() {
        return emailAddresses;
    }
    /**
     * EmailAddresses setter.
     * @param emails email addresses.
     */
    public void setEmailAddresses(final EmailAddress[] emails) {
        this.emailAddresses = emails;
    }
}
