
package fr.ynov.dap.outlookService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
    private String id;
    private String givenName;
    private String surname;
    private String companyName;
    private EmailAddress[] emailAddresses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(final String newGivenName) {
        this.givenName = newGivenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String newSurname) {
        this.surname = newSurname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String newCompanyName) {
        this.companyName = newCompanyName;
    }

    public EmailAddress[] getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(final EmailAddress[] newEmailAddresses) {
        this.emailAddresses = newEmailAddresses;
    }
}
