package com.ynov.dap.model.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Event.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

	/** The id. */
	private String id;

	/** The subject. */
	private String subject;

	/** The organizer. */
	private Recipient organizer;

	/** The start. */
	private DateTimeTimeZone start;

	/** The end. */
	private DateTimeTimeZone end;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Gets the organizer.
	 *
	 * @return the organizer
	 */
	public Recipient getOrganizer() {
		return organizer;
	}

	/**
	 * Sets the organizer.
	 *
	 * @param organizer the new organizer
	 */
	public void setOrganizer(Recipient organizer) {
		this.organizer = organizer;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public DateTimeTimeZone getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	public void setStart(DateTimeTimeZone start) {
		this.start = start;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public DateTimeTimeZone getEnd() {
		return end;
	}

	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	public void setEnd(DateTimeTimeZone end) {
		this.end = end;
	}
}
