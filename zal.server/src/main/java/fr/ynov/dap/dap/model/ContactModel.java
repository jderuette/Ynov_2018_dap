package fr.ynov.dap.dap.model;

/**
 * The Class ContactModel.
 */
public class ContactModel {
	
	/** The number of contact. */
	private Integer numberOfContact;

	
	/**
	 * Gets the number of contact.
	 *
	 * @return the number of contact
	 */
	public Integer getNumberOfContact() {
		return numberOfContact;
	}
	
	/**
	 * Sets the number of contact.
	 *
	 * @param numberContact the new number of contact
	 */
	public void setNumberOfContact(final Integer numberContact) {
		this.numberOfContact = numberContact;
	}

	
	/**
	 * Instantiates a new contact model.
	 *
	 * @param numberContact the number contact
	 */
	public ContactModel(final Integer numberContact){
		this.numberOfContact = numberContact;
	}
}
