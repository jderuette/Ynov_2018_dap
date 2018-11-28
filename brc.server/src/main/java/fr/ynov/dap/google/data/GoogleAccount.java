package fr.ynov.dap.google.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * The Class GoogleAccount.
 */
@Entity
public class GoogleAccount {

	/** The id. */
	@Id
	@GeneratedValue
	private int id;
	
	/** The name. */
	private String name;
	
	/** The owner. */
	@ManyToOne
	private AppUser owner;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

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
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public AppUser getOwner() {
		return owner;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(AppUser owner) {
		this.owner = owner;
	}
	
	
}
