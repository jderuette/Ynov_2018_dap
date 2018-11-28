package fr.ynov.dap.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * ToDo.
 * @author Robin DUDEK
 *
 */
@Entity
public class GoogleAccount {

    /**
     * Unique.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Owner column.
     */
    @ManyToOne
    private AppUser owner;

    /**
     * Account name column.
     */
    @Column
    private String accountName;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param val the id to set
     */
    public void setId(final Integer val) {
        this.id = val;
    }

    /**
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param val the owner to set
     */
    public void setOwner(final AppUser val) {
        this.owner = val;
    }

    /**
     * @return the userKey
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param val the userKey to set
     */
    public void setAccountName(final String val) {
        this.accountName = val;
    }

}
