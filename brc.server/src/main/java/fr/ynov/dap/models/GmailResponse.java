package fr.ynov.dap.models;

/**
 * The Class GmailResponse.
 */
public class GmailResponse {

	/** The nb mail. */
	private int nbMail;
	
	/** The nb unread mail. */
	private int nbUnreadMail;
	
	/**
	 * Gets the nb mail.
	 *
	 * @return the nb mail
	 */
	public int getNbMail() {
		return nbMail;
	}

	/**
	 * Sets the nb mail.
	 *
	 * @param nbMail the new nb mail
	 */
	public void setNbMail(int nbMail) {
		this.nbMail = nbMail;
	}

	/**
	 * Gets the nb unread mail.
	 *
	 * @return the nb unread mail
	 */
	public int getNbUnreadMail() {
		return nbUnreadMail;
	}

	/**
	 * Sets the nb unread mail.
	 *
	 * @param nbUnreadMail the new nb unread mail
	 */
	public void setNbUnreadMail(int nbUnreadMail) {
		this.nbUnreadMail = nbUnreadMail;
	}

	/**
	 * Instantiates a new gmail response.
	 *
	 * @param nbMail the nb mail
	 * @param nbUnreadMail the nb unread mail
	 */
	public GmailResponse(int nbMail, int nbUnreadMail) {
		this.nbMail = nbMail;
		this.nbUnreadMail = nbUnreadMail;
	}
	
}
