package fr.ynov.dap.dap.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.ynov.dap.dap.auth.TokenResponse;

@Entity
public class MicrosoftAccount {
	
	
	@Id
    @GeneratedValue
    private Integer id;

    /**
     * Owner column.
     */
	
	@ManyToOne
    private AppUser owner;

    /**
     * Account name column.
     */
    @Column
    private String email;
    
    @Column
    private String name;
    
    @Column
    private String tenantId;

    /**
     * List of every google account for this user.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id")
    private TokenResponse token;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AppUser getOwner() {
		return owner;
	}

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public TokenResponse getToken() {
		return token;
	}

	public void setToken(TokenResponse token) {
		this.token = token;
	}

	/**
     * User tenant id column.
     */
    

}
