package fr.ynov.dap.metier;

import fr.ynov.dap.outlookService.Message;

public class MessageOutlook {
	private String AccountName;
	private Message[] messages;

	/**
	 * @param accountName
	 * @param messages
	 */
	public MessageOutlook(String accountName, Message[] messages) {
		super();
		AccountName = accountName;
		this.messages = messages;
	}

	/**
	 * 
	 */
	public MessageOutlook() {
		super();

	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return AccountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		AccountName = accountName;
	}

	/**
	 * @return the messages
	 */
	public Message[] getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(Message[] messages) {
		this.messages = messages;
	}

}
