package fr.ynov.dap.dap.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 *
 * @author David_tepoche
 *
 */
@Entity
public class GoogleAccount {

    /**
     * primaryKey of GoogleAccount.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * the alias for the googleAccount.
     */
    @Column
    private String accountName;

    /**
     * foreign Key from AppUSer.
     */
    @ManyToOne
    @JsonBackReference
    private AppUser owner;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param idGeneratedValue the id to set
     */
    public void setId(final Integer idGeneratedValue) {
        this.id = idGeneratedValue;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param acntName the accountName to set
     */
    public void setAccountName(final String acntName) {
        this.accountName = acntName;
    }

    /**
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param ownerOfTheGoogleAccount the owner to set
     */
    public void setOwner(final AppUser ownerOfTheGoogleAccount) {
        this.owner = ownerOfTheGoogleAccount;
    }

}
