package fr.ynov.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Classe qui permet de gérer la table googleaccount en base de données.
 * @author abaracas
 *
 */
@Entity
public class GoogleAccount {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    private AppUser owner;
    private String accountName;

    /**
     * Crée un compte google
     * @param owner l'utilisateur applicatif lié
     * @param accountName le nom du compte google
     */
    public GoogleAccount(AppUser owner, String accountName) {
	super();
	setOwner(owner);
	setAccountName(accountName);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    
}
