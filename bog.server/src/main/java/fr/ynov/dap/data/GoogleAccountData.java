package fr.ynov.dap.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Mon_PC
 *
 */
@Entity
public class GoogleAccountData {
    /**.
     * Attribut id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**.
     * Attribut accountName
     */
    @Column(unique = true)
    private String accountName;
    /**.
     * Attribut owner
     */
    @ManyToOne
    private AppUser owner;

    /**
     * @param newOwner AppUser
     */
    public void setOwner(final AppUser newOwner) {
        owner = newOwner;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param value of the new accountName
     */
    public void setAccountName(final String value) {
        accountName = value;
    }
}
