package fr.ynov.dap.models;

/**
 * The Class ContactResponse.
 */
public class ContactResponse {

	/** The nb contact. */
	private int nbContact;

	/**
	 * Gets the nb contact.
	 *
	 * @return the nb contact
	 */
	public int getNbContact() {
		return nbContact;
	}

	/**
	 * Sets the nb contact.
	 *
	 * @param nbContact the new nb contact
	 */
	public void setNbContact(int nbContact) {
		this.nbContact = nbContact;
	}
	
	/**
	 * Instantiates a new contact response.
	 *
	 * @param nbContact the nb contact
	 */
	public ContactResponse(int nbContact){
		this.nbContact = nbContact;
	}
}
