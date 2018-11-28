package fr.ynov.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

/**
 * The Class GoogleAccount.
 */
@Entity
public class GoogleAccount {

	/** The id. */
	@Id
	@GeneratedValue
	Integer id;

	/** The owner. */
	@ManyToOne
	AppUser owner;

	/** The name. */
	String name;

	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(AppUser owner) {
		this.owner = owner;
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
}
