package fr.ynov.dap.dap.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author David_tepoche
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftContact {
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
     * @param id the id to set
     */
    public void setId(final String id) {
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
    public void setGivenName(final String givenName) {
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
    public void setSurname(final String surname) {
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
    public ArrayList<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    /**
     * @param emailAddresses the emailAddresses to set
     */
    public void setEmailAddresses(ArrayList<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }
}
