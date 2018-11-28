package fr.ynov.dap.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * The Class MicrosoftAccount.
 */
@Entity
public class MicrosoftAccount {

	/** The id. */
	@Id
	@GeneratedValue
	Integer id;

	/** The owner. */
	@ManyToOne
	AppUser owner;

	/** The name. */
	String name;

	/** The tenant id. */
	@Column
	String tenantId;

	/** The token. */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "token_id")
	TokenResponse token;

	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(AppUser owner) {
		this.owner = owner;
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

	/**
	 * Gets the tenant id.
	 *
	 * @return the tenant id
	 */
	public String getTenantId() {
		return tenantId;
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
	 * Sets the tenant id.
	 *
	 * @param tenantId the new tenant id
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(TokenResponse token) {
		this.token = token;
	}
}
