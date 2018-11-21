package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class MailModel.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailModel {
	
	/** The unread messages. */
	private Integer unreadMessages;
	
	/** The type. */
	private String type;
	
	/** The total messages. */
	private Integer totalMessages;
	
	/**
	 * Gets the total messages.
	 *
	 * @return the total messages
	 */
	public Integer getTotalMessages() {
		return totalMessages;
	}
    
	/**
	 * Sets the total messages.
	 *
	 * @param totalMessages the new total messages
	 */
	public void setTotalMessages(Integer totalMessages) {
		this.totalMessages = totalMessages;
	}
    
	/**
	 * Gets the unread messages.
	 *
	 * @return the unread messages
	 */
	public Integer getUnreadMessages() {
		return unreadMessages;
	}
	
	/**
	 * Sets the unread messages.
	 *
	 * @param unreadMessages the new unread messages
	 */
	public void setUnreadMessages(Integer unreadMessages) {
		this.unreadMessages = unreadMessages;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	
	/**
	 * Instantiates a new mail model.
	 *
	 * @param tm the tm
	 * @param um the um
	 * @param t the t
	 */
	public MailModel(final Integer tm, final Integer um, final String t) {
		this.totalMessages = tm;
		this.unreadMessages = um;
		this.type = t;
	}

	public MailModel() {
	}
}
