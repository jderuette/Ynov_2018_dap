package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Dom .
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
    /**
     *
     */
    private String id;
    /**
     *
     */
    private String givenName;

    /**
     *
     */
    private String surname;
    /**
     *
     */
    private String companyName;
    /**
     *
     */
    private EmailAddress[] emailAddresses;

    /**
     *
     * @return .
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param mId .
     */
    public void setId(final String mId) {
        this.id = mId;
    }

    /**
     *
     * @return .
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     *
     * @param mGivenName .
     */
    public void setGivenName(final String mGivenName) {
        this.givenName = mGivenName;
    }

    /**
     *
     * @return .
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param mSurname .
     */
    public void setSurname(final String mSurname) {
        this.surname = mSurname;
    }

    /**
     *
     * @return .
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param mCompanyName .
     */
    public void setCompanyName(final String mCompanyName) {
        this.companyName = mCompanyName;
    }

    /**
     *
     * @return .
     */
    public EmailAddress[] getEmailAddresses() {
        return emailAddresses;
    }

    /**
     *
     * @param mEmailAddresses .
     */
    public void setEmailAddresses(final EmailAddress[] mEmailAddresses) {
        this.emailAddresses = mEmailAddresses;
    }
}
