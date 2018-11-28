package fr.ynov.dap.dap.data.microsoft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class OutlookUser.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookUser {

	/** The id. */
	private String id;

	/** The mail. */
	private String mail;

	/** The display name. */
	private String displayName;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the mail.
	 *
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Sets the mail.
	 *
	 * @param emailAddress
	 *            the new mail
	 */
	public void setMail(String emailAddress) {
		this.mail = emailAddress;
	}

	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 *
	 * @param displayName
	 *            the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
