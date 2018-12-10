//TODO dur by Djer |POO| Par "habitude" on apelle "entity" des classe qui mappent des données vers une BDD. Ici on parlera plutot de DTO (Data Transfert Objet) ou de VO (Value Object) ou simplement de (MicrosoftGraph)Model"
package fr.ynov.dap.microsoft.entity;

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

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public EmailAddress[] getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(EmailAddress[] emailAddresses) {
        this.emailAddresses = emailAddresses;
    }
}