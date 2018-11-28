package com.ynov.dap.domain.microsoft;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.ynov.dap.domain.AppUser;
import com.ynov.dap.model.microsoft.TokenResponse;

/**
 * The Class MicrosoftAccount.
 */
@Entity
public class MicrosoftAccount {

	/** The id. */
	@Id
	@GeneratedValue
	private Integer id;

	/** The owner. */
	@ManyToOne
	private AppUser owner;
	
	/** The name. */
	private String name;
	
	/** The token response. */
	@OneToOne(cascade = CascadeType.ALL)
	private TokenResponse tokenResponse;
	
	/** The tenant id. */
	private String tenantId;
	
	/** The email. */
	private String email;
	
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
	 * @param id the new id
	 */
	public void setId(final Integer id) {
		this.id = id;
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
	 * @param owner the new owner
	 */
	public void setOwner(final AppUser owner) {
		this.owner = owner;
	}
	
    /**
     * Gets the token response.
     *
     * @return the token response
     */
    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    /**
     * Sets the token response.
     *
     * @param val the new token response
     */
    public void setTokenResponse(final TokenResponse val) {
        this.tokenResponse = val;
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
     * @param val the new tenant id
     */
    public void setTenantId(final String val) {
        this.tenantId = val;
    }
    
    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param val the new email
     */
    public void setEmail(final String val) {
        this.email = val;
    }

}