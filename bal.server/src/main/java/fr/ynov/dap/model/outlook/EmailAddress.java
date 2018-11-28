package fr.ynov.dap.model.outlook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress {

    private String name;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(final String val) {
        this.name = val;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String val) {
        this.address = val;
    }
}
