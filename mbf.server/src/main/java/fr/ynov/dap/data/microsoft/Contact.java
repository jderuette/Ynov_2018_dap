package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
    private String id;
    private String givenName;
    private String surname;
    private String companyName;
    private EmailAddress[] emailAddresses;

    public final String getId() {
        return id;
    }
    public final void setId(String identifier) {
        this.id = identifier;
    }
    public final String getGivenName() {
        return givenName;
    }
    public final void setGivenName(String name) {
        this.givenName = name;
    }
    public final String getSurname() {
        return surname;
    }
    public final void setSurname(String name) {
        this.surname = name;
    }
    public final String getCompanyName() {
        return companyName;
    }
    public final void setCompanyName(String name) {
        this.companyName = name;
    }
    public final EmailAddress[] getEmailAddresses() {
        return emailAddresses;
    }
    public final void setEmailAddresses(EmailAddress[] addresses) {
        this.emailAddresses = addresses;
    }
}