package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    /**
     * @param account Google
     */
    public void addGoogleAccount(final GoogleAccountData account) {
        account.setOwner(this);
        this.getAccounts().add(account);
    }

    /**
     * @return la liste google account
     */
    public List<GoogleAccountData> getAccounts() {
        return googleAccounts;
    }

    /**
     * @param value name
     */
    public void setName(final String value) {
        name = value;
    }
}
