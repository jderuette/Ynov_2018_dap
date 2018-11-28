package fr.ynov.dap.models;

import java.util.ArrayList;

import fr.ynov.dap.microsoft.models.Message;

/**
 * The Class AccountMailResponse.
 */
public class AccountMailResponse {

	/** The name. */
	private String name;
	
	/** The mails. */
	private ArrayList<Message> mails;
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the mails.
	 *
	 * @return the mails
	 */
	public ArrayList<Message> getMails() {
		return mails;
	}
	
	/**
	 * Sets the mails.
	 *
	 * @param mails the new mails
	 */
	public void setMails(ArrayList<Message> mails) {
		this.mails = mails;
	}
	
	
}
