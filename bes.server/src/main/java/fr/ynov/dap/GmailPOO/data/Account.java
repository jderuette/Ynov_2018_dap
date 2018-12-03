/**
 * 
 */
package fr.ynov.dap.GmailPOO.data;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.ynov.Outlook.auth.TokenResponse;

/**
 * @author sbr
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//TODO bes by Djer |POO| utiliser "cpt" ("Compte en FR je supose") n'est aps top tu aurais pu rester sur "account" (ou "acc" en abregé)
@DiscriminatorColumn(name = "TYPE_CPT", discriminatorType = DiscriminatorType.STRING, length = 9)
public class Account {
	@Id
	@GeneratedValue
	int id;
	String accountName;
	String adrMail;
	@ManyToOne
	AppUser owner;

	/**
	 * 
	 */
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param accountName
	 * @param adrMail
	 * @param owner
	 */
	public Account(String accountName, String adrMail, AppUser owner) {
		super();
		this.accountName = accountName;
		this.adrMail = adrMail;
		this.owner = owner;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the adrMail
	 */
	public String getAdrMail() {
		return adrMail;
	}

	/**
	 * @param adrMail the adrMail to set
	 */
	public void setAdrMail(String adrMail) {
		this.adrMail = adrMail;
	}

	/**
	 * @return the owner
	 */
	public AppUser getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	
}
