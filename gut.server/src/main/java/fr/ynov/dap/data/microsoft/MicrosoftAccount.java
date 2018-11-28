package fr.ynov.dap.data.microsoft;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.ynov.dap.data.AppUser;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class MicrosoftAccount {

	private static final Logger logger = LogManager.getLogger();
	
	@Id
	@JsonProperty("Id")
	private String id;
	@JsonProperty("EmailAddress")
	private String emailAddress;
	@JsonProperty("DisplayName")
	private String displayName;
	@JsonProperty("Alias")
	private String alias;
	@JsonProperty("MailboxGuid")
	private String mailboxGuid;
	
	@JsonProperty("AccessToken")
	private String accessToken;
	@JsonProperty("Username")
	private String username;
	@JsonProperty("UserTenantID")
	private String userTenantID;
	
	@ManyToOne
	AppUser appUser;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getMailboxGuid() {
		return mailboxGuid;
	}
	public void setMailboxGuid(String mailboxGuid) {
		this.mailboxGuid = mailboxGuid;
	}
	public void setOwner(AppUser appUser) {
		logger.debug("Binding AppUser <-> GoogleAccount");
        this.appUser = appUser;        
        if (!appUser.getMicrosoftAccounts().contains(this)) { // warning this may cause performance issues if you have a large data set since this operation is O(n)
        	appUser.getMicrosoftAccounts().add(this);
        }		
	}
}
