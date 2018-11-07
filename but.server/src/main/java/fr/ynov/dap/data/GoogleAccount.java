package fr.ynov.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue
    private Integer id;

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
