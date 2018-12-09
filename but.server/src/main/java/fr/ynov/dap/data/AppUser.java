package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.data.google.GoogleAccount;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;

/**
 * Model for AppUser.
 * @author thibault
 *
 */
@Entity
public class AppUser {

    /**
     * Id of entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * User key.
     */
    @Column(unique = true)
    private String userKey;

    /**
     * Google accounts of user.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccounts;

    /**
     * Microsoft accounts of user.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<MicrosoftAccount> microsoftAccounts;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the googleAccounts
     */
    public List<GoogleAccount> getGoogleAccounts() {
        return googleAccounts;
    }

    /**
     * @param gAccounts the googleAccounts to set
     */
    public void setGoogleAccounts(final List<GoogleAccount> gAccounts) {
        this.googleAccounts = gAccounts;
    }

    /**
     * Add a google account of this user.
     * @param account the GoogleAccount to add
     */
    //TODO but by Djer |POO| Evite de melanger tes methodes "metier" au millieu de tes getters/setter. Ici tu as regroupé ce qui concerne les "GoogleAccount" et les "MicrosoftAcocunt", tu suis une logique, mais "piègeuse". Si tu as des méthodes qui "doivent" être groupées enssembles au seing d'une autre classe, c'est surement que tu devrais en extraire une nouvelle classe (ici une "extenssion" de collection ce qui est un peu étrange ....)
    public void addGoogleAccount(final GoogleAccount account) {
        account.setOwner(this);
        this.getGoogleAccounts().add(account);
    }

    /**
     * @return the microsoftAccounts
     */
    public List<MicrosoftAccount> getMicrosoftAccounts() {
        return microsoftAccounts;
    }

    /**
     * @param mAccounts the microsoftAccounts to set
     */
    public void setMicrosoftAccounts(final List<MicrosoftAccount> mAccounts) {
        this.microsoftAccounts = mAccounts;
    }

    /**
     * Add a Microsoft account of this user.
     * @param account the MicrosoftAccount to add
     */
    public void addMicrosoftAccount(final MicrosoftAccount account) {
        account.setOwner(this);
        this.getMicrosoftAccounts().add(account);
    }

    /**
     * @return the userKey
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * @param uKey the userKey to set
     */
    public void setUserKey(final String uKey) {
        this.userKey = uKey;
    }
}
