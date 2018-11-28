package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author David_tepoche
 *
 */
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param nm the name to set
     */
    public void setName(final String nm) {
        this.name = nm;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param adrs the address to set
     */
    public void setAddress(final String adrs) {
        this.address = adrs;
    }
}
