package fr.ynov.dap.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Florian
 */
@Entity
public class GoogleAccountData {

    /**.
     * declaration de l'id
     */
    @Id
    @GeneratedValue
    private int id;

    /**.
     * declaration de owner
     */
    @ManyToOne
    private AppUser owner;

    /**.
     * Déclaration du nom de compte
     */
    private String accountName;

    /**.
     * recuperation de owner
     * @return owner
     */
    protected AppUser getOwner() {
        return owner;
    }

    /**.
     * @param theOwner Donne une valeur a owner
     */
    protected void setOwner(final AppUser theOwner) {
        this.owner = theOwner;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param theAccountName the theAccountName to set
     */
    public void setAccountName(final String theAccountName) {
        this.accountName = theAccountName;
    }
}
