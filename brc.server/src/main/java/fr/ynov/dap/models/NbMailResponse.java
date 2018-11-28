package fr.ynov.dap.models;


/**
 * The Class NbMailResponse.
 */
public class NbMailResponse {

	/** The nb unread mail. */
	private int nbUnreadMail;

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
	 * @param nbUnreadMail the nb unread mail
	 */
	public NbMailResponse(int nbUnreadMail) {
		this.nbUnreadMail = nbUnreadMail;
	}
	
}
