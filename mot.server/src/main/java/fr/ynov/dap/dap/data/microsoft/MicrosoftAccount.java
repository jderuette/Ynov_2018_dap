package fr.ynov.dap.dap.data.microsoft;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.ynov.dap.dap.data.google.AppUser;

/**
 * The Class MicrosoftAccount.
 */
@Entity
public class MicrosoftAccount {
	@Id
	@GeneratedValue
	Integer id;

	@ManyToOne
	AppUser owner;

	@Column
	String tenantId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "token_id")
	TokenResponse token;

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	public String getTenantId() {
		return tenantId;
	}

	public TokenResponse getToken() {
		return token;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setToken(TokenResponse token) {
		this.token = token;
	}
}
