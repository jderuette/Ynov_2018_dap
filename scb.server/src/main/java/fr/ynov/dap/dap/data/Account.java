package fr.ynov.dap.dap.data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Account {
	
	@Id
	@GeneratedValue
	int id;
	public enum AccountType{
		Outlook,
		Google
	}
	
	AccountType accountType;
	
	public void setAccountType(AccountType ac){
		accountType = ac;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
}
