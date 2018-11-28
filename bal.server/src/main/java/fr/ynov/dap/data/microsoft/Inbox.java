/*
 * 
 */
package fr.ynov.dap.data.microsoft;

import java.util.ArrayList;

import fr.ynov.dap.model.outlook.Message;

/**
 * The Class Inbox.
 */
public class Inbox {

	/** The account. */
    private MicrosoftAccount account;

	/** The messages. */
    private ArrayList<Message> messages;

	/**
	 * Gets the account.
	 *
	 * @return the account
	 */
    public MicrosoftAccount getAccount() {
        return account;
    }

	/**
	 * Sets the account.
	 *
	 * @param val the new account
	 */
    public void setAccount(final MicrosoftAccount val) {
        this.account = val;
    }

	/**
	 * Gets the messages.
	 *
	 * @return the messages
	 */
    public ArrayList<Message> getMessages() {
        return messages;
    }

	/**
	 * Sets the messages.
	 *
	 * @param val the new messages
	 */
    public void setMessages(final ArrayList<Message> val) {
        this.messages = val;
 }
}
