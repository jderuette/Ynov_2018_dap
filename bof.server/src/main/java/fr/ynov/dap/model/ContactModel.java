package fr.ynov.dap.model;

public class ContactModel extends MasterModel{
	public int getNbOfContact() {
		return nbOfContact;
	}


	public void setNbOfContact(int nbOfContact) {
		this.nbOfContact = nbOfContact;
	}


	private int nbOfContact;
	
	
	public ContactModel(int nbOfContact) {
		this.nbOfContact = nbOfContact;
	}
	
	
	
}
