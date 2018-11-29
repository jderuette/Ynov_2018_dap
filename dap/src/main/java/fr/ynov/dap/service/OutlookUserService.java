package fr.ynov.dap.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Défini les différents champs relatifs aux users microsoft.
 * @author abaracas
 *
 */
public class OutlookUserService {
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
	/**
	 * @return the id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
	    return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
	    this.emailAddress = emailAddress;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
	    return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
	    this.displayName = displayName;
	}
	/**
	 * @return the alias
	 */
	public String getAlias() {
	    return alias;
	}
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
	    this.alias = alias;
	}
	/**
	 * @return the mailboxGuid
	 */
	public String getMailboxGuid() {
	    return mailboxGuid;
	}
	/**
	 * @param mailboxGuid the mailboxGuid to set
	 */
	public void setMailboxGuid(String mailboxGuid) {
	    this.mailboxGuid = mailboxGuid;
	}
	
	
}
