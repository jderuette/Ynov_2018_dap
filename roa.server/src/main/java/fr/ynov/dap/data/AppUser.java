package fr.ynov.dap.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author alex
 *
 */
@Entity
public class AppUser {
    /**
     * id.
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * nom.
     */
    private String userKey;
    /**
     * liste des comptes googles.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccounts;
    /**
     * ajouter un compte à un utilisateur.
     * @param account GoogleAccount
     */
    public final void adGoogleAccount(final GoogleAccount account) {
        account.setOwner(this);
        this.getAccounts().add(account);
    }
    /**
     * récupérer la liste des comptes du proprio.
     * @return liste account name.
     */
    public final List<String> getAccountNames() {
        List<String> accountNames = new ArrayList<>();
        for (GoogleAccount googleAccount : googleAccounts) {
            accountNames.add(googleAccount.getAccountName());
        }
        return accountNames;
    }
    /**
     * @return liste des comptes
     */
    private List<GoogleAccount> getAccounts() {
        return this.googleAccounts;
    }
    /**
     * @param nam nom utilisateur
     */
    public final void setUserKey(final String nam) {
        this.userKey = nam;
    }
}
