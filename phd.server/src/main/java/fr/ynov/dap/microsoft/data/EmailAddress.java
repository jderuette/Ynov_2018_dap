package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Dom .
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress {
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String address;

    /**
     *
     * @return .
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param mName .
     */
    public void setName(final String mName) {
        this.name = mName;
    }

    /**
     *
     * @return .
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param mAdress .
     */
    public void setAddress(final String mAdress) {
        this.address = mAdress;
    }
}
