package fr.ynov.dap.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class MicrosoftAccount {
	@Id
	@GeneratedValue
	Integer id;

	@ManyToOne
	AppUser owner;

	String name;

	@Column
	String tenantId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "token_id")
	TokenResponse token;

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	public AppUser getOwner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
