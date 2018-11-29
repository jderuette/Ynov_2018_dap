package fr.ynov.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
/**
 * Classe utilisée pour construire la table appUser dans la base.
 * @author abaracas
 *
 */
public class AppUser {
    @Id
    @GeneratedValue
    private Integer id;
    
    private String name;
    

    @OneToMany (cascade = CascadeType.ALL, mappedBy="owner")
    private List<GoogleAccount> googleAccounts;
    
    @OneToMany (cascade = CascadeType.ALL, mappedBy="owner")
    private List<MicrosoftAccount> microsoftAccounts;
    
    public AppUser(String name) {
	super();
	this.name = name;
    }
    
    public AppUser() {
    }    

    /**
     * Permet d'ajouter un compte google avec un owner spécifié.
     * @param account compte google
     */
    public void addGoogleAccount(GoogleAccount account){
	    account.setOwner(this);
	    this.getGoogleAccounts().add(account);
	}
    
    /**
     * Permet d'ajouter un compte microsoft avec un owner spécifié.
     * @param account compte microsoft
     */
    public void addMicrosoftAccounts(MicrosoftAccount account){
	    account.setOwner(this);
	    this.getMicrosoftAccounts().add(account);
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the googleAccounts
     */
    public List<GoogleAccount> getGoogleAccounts() {
        return googleAccounts;
    }

    /**
     * @param googleAccounts the googleAccounts to set
     */
    public void setGoogleAccounts(List<GoogleAccount> googleAccounts) {
        this.googleAccounts = googleAccounts;
    }

    /**
     * @return the microsoftAccounts
     */
    public List<MicrosoftAccount> getMicrosoftAccounts() {
        return microsoftAccounts;
    }

    /**
     * @param microsoftAccounts the microsoftAccounts to set
     */
    public void setMicrosoftAccounts(List<MicrosoftAccount> microsoftAccounts) {
        this.microsoftAccounts = microsoftAccounts;
    }    

}
