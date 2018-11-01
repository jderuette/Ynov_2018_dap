package fr.ynov.dap.dap.model;

import java.util.Date;

/**
 * The Class CalendarModel.
 */
public class CalendarModel {
    //TODO zal by Djer Ordre à respecter : constantes, attributs, constructeurs, méthpde "métier", getter/setter

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
	 * Gets the date of start.
	 *
	 * @return the date of start
	 */
	public Date getDateOfStart() {
		return dateOfStart;
	}

	/**
	 * Sets the date of start.
	 *
	 * @param dateOfStart the new date of start
	 */
	public void setDateOfStart(Date dateOfStart) {
		this.dateOfStart = dateOfStart;
	}

	/**
	 * Gets the date of end.
	 *
	 * @return the date of end
	 */
	public Date getDateOfEnd() {
		return dateOfEnd;
	}

	/**
	 * Sets the date of end.
	 *
	 * @param dateOfEnd the new date of end
	 */
	public void setDateOfEnd(Date dateOfEnd) {
		this.dateOfEnd = dateOfEnd;
	}

	/**
	 * Gets the choice.
	 *
	 * @return the choice
	 */
	public String getChoice() {
		return choice;
	}

	/**
	 * Sets the choice.
	 *
	 * @param choice the new choice
	 */
	public void setChoice(String choice) {
		this.choice = choice;
	}

	/** The subject. */
	private String subject;
	
	/** The date of start. */
	private Date dateOfStart;
	
	/** The date of end. */
	private Date dateOfEnd;
	
	/** The choice. */
	private String choice;
	
	/**
	 * Instantiates a new calendar model.
	 *
	 * @param subject the subject
	 * @param dateOfStart the date of start
	 * @param dateOfEnd the date of end
	 * @param choice the choice
	 */
	public CalendarModel(String subject, Date dateOfStart, Date dateOfEnd, String choice){
		this.subject = subject;
		this.dateOfEnd = dateOfEnd;
		this.dateOfStart = dateOfStart;
		this.choice = choice;
	}
}
