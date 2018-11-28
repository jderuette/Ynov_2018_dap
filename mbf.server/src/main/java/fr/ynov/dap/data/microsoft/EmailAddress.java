package fr.ynov.dap.data.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress {
    private String name;
    private String address;

    public final String getName() {
        return name;
    }
    public final void setName(String userName) {
        this.name = userName;
    }
    public final String getAddress() {
        return address;
    }
    public final void setAddress(String userAddress) {
        this.address = userAddress;
    }
}