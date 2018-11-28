package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import fr.ynov.dap.microsoft.data.MicrosoftAccountData;

/**
 * @author Mon_PC
 */
@Entity
public class AppUser {

    /**.
     * Attribut id
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**.
     * Attribut name
     */
    @Column(unique = true)
    private String name;
    /**.
     * Attribut liste de google account
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccountData> googleAccounts;

    /**.
     * Attribut liste de microsoft account
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<MicrosoftAccountData> microsoftAccounts;

    /**
     * @param account Google
     */
    public void addGoogleAccount(final GoogleAccountData account) {
        account.setOwner(this);
        this.getAccounts().add(account);
    }

    /**
     * @param account Microsoft
     */
    public void addMicrosoftAccount(final MicrosoftAccountData account) {
        account.setOwner(this);
        this.getAccountsMicrosoft().add(account);
    }

    /**
     * @return la liste google account
     */
    public List<GoogleAccountData> getAccounts() {
        return googleAccounts;
    }

    /**
     * @return la liste microsoft account
     */
    public List<MicrosoftAccountData> getAccountsMicrosoft() {
        return microsoftAccounts;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param value name
     */
    public void setName(final String value) {
        name = value;
    }
}
