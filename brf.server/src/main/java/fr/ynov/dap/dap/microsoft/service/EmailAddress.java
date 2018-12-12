package fr.ynov.dap.dap.microsoft.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Florian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress {
    /**.
     * Déclaration de name
     */
    private String name;
    /**.
     * Déclaration de l'adresse
     */
    private String address;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param theName mofication de la valeur
     */
    public void setName(final String theName) {
        this.name = theName;
    }

    /**
     * @return l'adresse
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param theAddress modification de la valeur
     */
    public void setAddress(final String theAddress) {
        this.address = theAddress;
    }
}
