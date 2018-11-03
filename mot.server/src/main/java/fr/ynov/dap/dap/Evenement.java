package fr.ynov.dap.dap;

import com.google.api.client.util.DateTime;

/**
 * The Class Evenement.
 */
public class Evenement {
	
	/** The statut. */
	private String statut;
	
	/** The date debut. */
	private DateTime dateDebut;
	
	/** The date fin. */
	private DateTime dateFin;
	
	/** The sujet. */
	private String sujet;
	
	/** The self. */
	private Boolean self;
	
	/**
	 *      Evenement Constructor.
	 *
	 * @param statut the statut
	 * @param dateTime the date time
	 * @param dateFin the date fin
	 * @param sujet the sujet
	 * @param self the self
	 */
	public Evenement(String statut, DateTime dateTime, DateTime dateFin, String sujet, Boolean self) {
		this.statut = statut;
		this.dateDebut = dateTime;
		this.dateFin = dateFin;
		this.sujet = sujet;
		this.self = self;
	}
	
	/**
	 * Instantiates a new evenement.
	 */
	public Evenement() {
	}
	
	/**
	 * Gets the statut.
	 *
	 * @return the statut
	 */
	public String getStatut() {
		return statut;
	}
	
	/**
	 * Sets the statut.
	 *
	 * @param statut the new statut
	 */
	public void setStatut(String statut) {
		this.statut = statut;
	}
	
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
	 * Gets the sujet.
	 *
	 * @return the sujet
	 */
	public String getSujet() {
		return sujet;
	}
	
	/**
	 * Sets the sujet.
	 *
	 * @param sujet the new sujet
	 */
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	/**
	 * Gets the self.
	 *
	 * @return the self
	 */
	public Boolean getSelf() {
		return self;
	}

	/**
	 * Sets the self.
	 *
	 * @param self the new self
	 */
	public void setSelf(Boolean self) {
		this.self = self;
	}
	
	
	
	
	
}
