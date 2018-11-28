package fr.ynov.dap.microsoft.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.ynov.dap.google.data.AppUser;


/**
 * The Class MicrosoftAccount.
 */
@Entity
public class MicrosoftAccount {

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
     * Account email column.
     */
    @Column
    private String email;
    
    /**
     * User tenant id column.
     */
    @Column
    private String tenantId;

    
    /**
     * Account name column.
     * 
     */
    @Column
    private String name;

    /**
     * List of every google account for this user.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id")
    private TokenResponse token;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param val the id to set
     */
    public void setId(final Integer val) {
        this.id = val;
    }

    /**
     * Gets the owner.
     *
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * Sets the owner.
     *
     * @param val the owner to set
     */
    public void setOwner(final AppUser val) {
        this.owner = val;
    }

    /**
     * Gets the email.
     *
     * @return the userKey
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param val the userKey to set
     */
    public void setEmail(final String val) {
        this.email = val;
    }

    /**
     * Gets the tenant id.
     *
     * @return the tenant id
     */
    public String getTenantId() {
		return tenantId;
	}

	/**
	 * Sets the tenant id.
	 *
	 * @param tenantId the new tenant id
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
    public TokenResponse getToken() {
        return token;
    }

    /**
     * Sets the token.
     *
     * @param val the token to set
     */
    public void setToken(final TokenResponse val) {
        this.token = val;
    }

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
    
    
}
