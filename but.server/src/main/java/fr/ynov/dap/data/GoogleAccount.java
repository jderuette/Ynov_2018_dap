package fr.ynov.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Model for GoogleAccount.
 * @author thibault
 *
 */
@Entity
public class GoogleAccount {

    /**
     * Id of entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Google Account Name.
     */
    private String accountName;

    /**
     * Owner of google account.
     */
    @ManyToOne
    private AppUser owner;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param name the accountName to set
     */
    public void setAccountName(final String name) {
        this.accountName = name;
    }

    /**
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param ownerUser the owner to set
     */
    public void setOwner(final AppUser ownerUser) {
        this.owner = ownerUser;
    }

}
