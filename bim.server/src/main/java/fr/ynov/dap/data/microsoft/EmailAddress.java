package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress {

    /**
     * Default constructor for serialization.
     */
    public EmailAddress() {
    }

    /**
     * @param name contact name
     * @param address contact address
     */
    public EmailAddress(String name, String address) {
        super();
        this.name = name;
        this.address = address;
    }

    /**
     * Name.
     */
    private String name;
    /**
     * Address.
     */
    private String address;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

}