package fr.ynov.dap.dap.google;


/**
 * The Class GmailResponse.
 */
public class GmailResponse {
	
	/** The nb mail un read. */
	private int nbMailUnRead;
	
	/**
	 * Instantiates a new gmail response.
	 *
	 * @param nbMailUnRead the nb mail un read
	 */
	public GmailResponse(int nbMailUnRead) {
		this.nbMailUnRead = nbMailUnRead;
	}

	/**
	 * Gets the nb mail un read.
	 *
	 * @return the nb mail un read
	 */
	public int getNbMailUnRead() {
		return nbMailUnRead;
	}

	/**
	 * Sets the nb mail un read.
	 *
	 * @param nbMailUnRead the new nb mail un read
	 */
	public void setNbMailUnRead(int nbMailUnRead) {
		this.nbMailUnRead = nbMailUnRead;
	}
	
	
	
}
