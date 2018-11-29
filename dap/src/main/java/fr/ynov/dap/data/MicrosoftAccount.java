package fr.ynov.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
/**
 * Permet de gérer la table MicrosoftAccount en base.
 * @author abaracas
 *
 */
public class MicrosoftAccount {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    private AppUser owner;
    private String accountName;
    private String email;
    private String tenantId;  

    /**
     * Crée le compte microsoft avec les bons paramètres.
     * @param owner l'utilisateur applicatif
     * @param accountName le nom du compte microsoft
     * @param email le mail lié
     * @param tenantId le tenant id lié
     */
    public MicrosoftAccount(AppUser owner, String accountName, String email, String tenantId) {
	super();
	setOwner(owner);
	setAccountName(accountName);
	setEmail(email);
	setTenantId(tenantId);
    }
    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }
    /**
     * @param tenantId the tenantId to set
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
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
     * @param owner the owner to set
     */
    public void setOwner(AppUser owner) {
        this.owner = owner;
    }
    
}
