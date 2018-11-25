package com.ynov.dap.microsoft.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.ynov.dap.data.AppUser;
import com.ynov.dap.microsoft.models.TokenResponse;

@Entity
public class MicrosoftAccount {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	private AppUser owner;
	
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
	private TokenResponse tokenResponse;
	
	private String tenantId;
	
	private String email;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public AppUser getOwner() {
		return owner;
	}

	public void setOwner(final AppUser owner) {
		this.owner = owner;
	}
	
    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(final TokenResponse val) {
        this.tokenResponse = val;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(final String val) {
        this.tenantId = val;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(final String val) {
        this.email = val;
    }

}