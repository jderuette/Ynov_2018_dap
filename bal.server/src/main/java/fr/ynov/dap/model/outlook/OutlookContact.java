package fr.ynov.dap.model.outlook;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookContact {

    private String id;

    private String givenName;

    private String surname;

    private String companyName;

    private ArrayList<EmailAddress> emailAddresses;

    public String getId() {
        return id;
    }

    public void setId(final String val) {
        this.id = val;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(final String val) {
        this.givenName = val;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String val) {
        this.surname = val;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String val) {
        this.companyName = val;
    }

    public ArrayList<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(final ArrayList<EmailAddress> val) {
        this.emailAddresses = val;
    }

}