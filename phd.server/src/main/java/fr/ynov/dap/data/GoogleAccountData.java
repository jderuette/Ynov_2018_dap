package fr.ynov.dap.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Dom
 *
 */
@Entity
public class GoogleAccountData {
    /**
     * .
     */
    @Column(unique = true)
    private String accountName;

    //TODO phd by Djer |POO| Ne mélange pas des getter/setter avec tes attributs. ordre : constantes, attributs, initialisateur statics, constructeurs, méthodes métiers, méthodes génériques (toString, hashCode,...) getter/setter

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param mAccountName the accountName to set
     */
    public void setAccountName(final String mAccountName) {
        this.accountName = mAccountName;
    }

    /**
     * .
     */
    @Id
    @GeneratedValue
    private int id;

    /**
     * .
     */
    @ManyToOne
    private AppUser owner;

    /**
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param mOwner the owner to set
     */
    public void setOwner(final AppUser mOwner) {
        this.owner = mOwner;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param mId the id to set
     */
    public void setId(final int mId) {
        this.id = mId;
    }

}
