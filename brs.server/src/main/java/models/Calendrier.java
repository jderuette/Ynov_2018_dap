package models;

import com.google.api.client.util.DateTime;

/**
 * The Class Calendrier.
 */
public class Calendrier {

	/**
	 * Gets the date debut.
	 *
	 * @return the date debut
	 */
	public DateTime getDateDebut() {
		return dateDebut;
	}

	/**
	 * Sets the date debut.
	 *
	 * @param dateDebut the new date debut
	 */
	public void setDateDebut(DateTime dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * Gets the date fin.
	 *
	 * @return the date fin
	 */
	public DateTime getDateFin() {
		return dateFin;
	}

	/**
	 * Sets the date fin.
	 *
	 * @param dateFin the new date fin
	 */
	public void setDateFin(DateTime dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the self.
	 *
	 * @return the self
	 */
	public String getSelf() {
		return self;
	}

	/**
	 * Sets the self.
	 *
	 * @param self the new self
	 */
	public void setSelf(String self) {
		this.self = self;
	}

	/** The date debut. */
	private DateTime dateDebut;

	/**
	 * Instantiates a new calendrier.
	 *
	 * @param dateDebut the date debut
	 * @param dateFin   the date fin
	 * @param subject   the subject
	 * @param status    the status
	 * @param self      the self
	 */
	public Calendrier(DateTime dateDebut, DateTime dateFin, String subject, String status, String self) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.subject = subject;
		this.status = status;
		this.self = self;
	}

	/** The date fin. */
	private DateTime dateFin;

	/** The subject. */
	private String subject;

	/** The status. */
	private String status;

	/** The self. */
	private String self;
}
