package fr.ynov.dap.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entity EmailAddress.
 * @author thibault
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress {
    /**
     * Name.
     */
    private String name;
    /**
     * Email.
     */
    private String address;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param nameToSet the name to set
     */
    public void setName(final String nameToSet) {
        this.name = nameToSet;
    }
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param addressToSet the address to set
     */
    public void setAddress(final String addressToSet) {
        this.address = addressToSet;
    }
}
