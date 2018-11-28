package fr.ynov.dap.dap.data.google;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import fr.ynov.dap.dap.data.AppUser;

/**
 * The Class GoogleAccount.
 */
@Entity
public class GoogleAccount {

	/** The id. */
	@Id
	@GeneratedValue
	private int id;

	/** The owner. */
	@ManyToOne
	private AppUser owner;

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
	 * @param owner
	 *            the new owner
	 */
	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	/** The name. */
	private String name;

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
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
