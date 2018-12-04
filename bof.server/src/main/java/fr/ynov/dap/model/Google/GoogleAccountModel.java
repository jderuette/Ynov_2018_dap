package fr.ynov.dap.model.Google;

//TODO bof by Djer |IDE| Organise tes imports, ca évitera ces "import globaux" qui sont en général une fausse bonne idée
import javax.persistence.*;

import fr.ynov.dap.model.AppUserModel;
import fr.ynov.dap.model.MasterModel;

@Entity
public class GoogleAccountModel extends MasterModel {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String accountName;
	
	//TODO bof by Djer |POO|Evite des getter/setter au milieu des attributs
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
