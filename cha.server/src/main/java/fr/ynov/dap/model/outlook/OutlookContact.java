package fr.ynov.dap.model.outlook;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class OutlookContact.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookContact {

    /**
     * Store Contact id.
     */
    private String id;

    /**
     * Store given name.
     */
    private String givenName;

    /**
     * Store surname.
     */
    private String surname;

    /**
     * Store company name.
     */
    private String companyName;

    /**
     * Store email addresses.
     */
    private ArrayList<EmailAddress> emailAddresses;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param val the id to set
     */
    public void setId(final String val) {
        this.id = val;
    }

    /**
     * Gets the given name.
     *
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Sets the given name.
     *
     * @param val the givenName to set
     */
    public void setGivenName(final String val) {
        this.givenName = val;
    }

    /**
     * Gets the surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     *
     * @param val the surname to set
     */
    public void setSurname(final String val) {
        this.surname = val;
    }

    /**
     * Gets the company name.
     *
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the company name.
     *
     * @param val the companyName to set
     */
    public void setCompanyName(final String val) {
        this.companyName = val;
    }

    /**
     * Gets the email addresses.
     *
     * @return the emailAddresses
     */
    public ArrayList<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    /**
     * Sets the email addresses.
     *
     * @param val the emailAddresses to set
     */
    public void setEmailAddresses(final ArrayList<EmailAddress> val) {
        this.emailAddresses = val;
    }

}