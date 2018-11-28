package fr.ynov.dap.microsoft.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Mon_PC
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress {
    /**.
     * propriété name
     */
    private String name;
    /**.
     * propriété address
     */
    private String address;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**.
     * Set new name
     * @param newName correspondant au nouveau name
     */
    public void setName(final String newName) {
        this.name = newName;
    }

    /**
     * @return addres
     */
    public String getAddress() {
        return address;
    }

    /**.
     * Set new address
     * @param newAddress correspondant à la nouvelle address
     */
    public void setAddress(final String newAddress) {
        this.address = newAddress;
    }
}
