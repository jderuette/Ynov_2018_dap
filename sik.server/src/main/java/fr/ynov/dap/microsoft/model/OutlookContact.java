package fr.ynov.dap.microsoft.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represent a contact from Microsoft Graph API.
 * @author Kévin Sibué
 *
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
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param val the id to set
     */
    public void setId(final String val) {
        this.id = val;
    }

    /**
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @param val the givenName to set
     */
    public void setGivenName(final String val) {
        this.givenName = val;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param val the surname to set
     */
    public void setSurname(final String val) {
        this.surname = val;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param val the companyName to set
     */
    public void setCompanyName(final String val) {
        this.companyName = val;
    }

    /**
     * @return the emailAddresses
     */
    public ArrayList<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    /**
     * @param val the emailAddresses to set
     */
    public void setEmailAddresses(final ArrayList<EmailAddress> val) {
        this.emailAddresses = val;
    }

}
