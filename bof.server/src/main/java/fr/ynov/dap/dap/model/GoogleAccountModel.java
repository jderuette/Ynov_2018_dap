package fr.ynov.dap.dap.model;

import javax.persistence.*;

@Entity
public class GoogleAccountModel extends MasterModel {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String accountName;
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@ManyToOne
	private AppUserModel appUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AppUserModel getOwner() {
		return appUser;
	}

	public void setOwner(AppUserModel appUser) {
		this.appUser = appUser;
	}
	
}
