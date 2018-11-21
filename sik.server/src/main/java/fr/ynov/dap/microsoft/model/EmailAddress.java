package fr.ynov.dap.microsoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress {

    /**
     * Store name.
     */
    private String name;

    /**
     * Store address.
     */
    private String address;

    /**
     * Return name.
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     * @param val name.
     */
    public void setName(final String val) {
        this.name = val;
    }

    /**
     * Return addresses.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set new address.
     * @param val Address
     */
    public void setAddress(final String val) {
        this.address = val;
    }
}
