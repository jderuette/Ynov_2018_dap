package fr.ynov.dap.dap.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * The Class OutlookAccount.
 */
@Entity
public class OutlookAccount {
	
	/** The id. */
	@Id
	@GeneratedValue
	private int id;
	
	/** The name. */
	private String name;
	
	/** The token. */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "owner")
	private Token token;

	/** The owner. */
	@ManyToOne
	private AppUser owner;
	
	/** The tenant id. */
	private String tenantId;
	

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
	 * Adds the token.
	 *
	 * @param token the token
	 */
	public void addToken(Token token){
		this.token = token;
	    token.setOwner(this);
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
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
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Gets the token.
	 *
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param tokenResponse the new token
	 */
	public void setToken(Token tokenResponse) {
		this.token = tokenResponse;
	}
	
	
}
