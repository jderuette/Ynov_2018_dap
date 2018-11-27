package fr.ynov.dap.services.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * EmailAddress entity used by the Microsoft API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress {
    /**
     * Name of the emailAddress.
     */
    private String name;
    /**
     * Address of the emailAddress.
     */
    private String address;

    /**
     * Name getter.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter.
     * @param n name.
     */
    public void setName(final String n) {
        this.name = n;
    }

    /**
     * Address getter.
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Address setter.
     * @param a address.
     */
    public void setAddress(final String a) {
        this.address = a;
    }
}
