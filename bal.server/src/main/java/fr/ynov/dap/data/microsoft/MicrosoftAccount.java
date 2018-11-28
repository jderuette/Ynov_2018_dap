/*
 * 
 */
package fr.ynov.dap.data.microsoft;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.ynov.dap.data.AppUser;

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

	/** The email. */
    @Column
    private String email;

	/** The tenant id. */
    @Column
    private String tenantId;

	/** The account name. */
    @Column
    private String accountName;

	/** The token. */
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
	 * @param val the new id
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
	 * @param val the new owner
	 */
    public void setOwner(final AppUser val) {
        this.owner = val;
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
	 * @param val the new token
	 */
    public void setToken(final TokenResponse val) {
        this.token = val;
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
	 * Gets the account name.
	 *
	 * @return the account name
	 */
    public String getAccountName() {
        return accountName;
    }

	/**
	 * Sets the account name.
	 *
	 * @param val the new account name
	 */
    public void setAccountName(final String val) {
        this.accountName = val;
    }
}
