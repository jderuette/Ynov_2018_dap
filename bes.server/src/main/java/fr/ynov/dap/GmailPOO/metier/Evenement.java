/**
 * 
 */
package fr.ynov.dap.GmailPOO.metier;

import java.util.Date;

import com.google.api.client.util.DateTime;

import fr.ynov.Outlook.service.DateTimeTimeZone;

/**
 * @author acer
 *
 */

public class Evenement {
private String organizer;
private String subject;
private Date startDate;
private Date endDate;


/**
 * 
 */
public Evenement() {
	super();
	// TODO Auto-generated constructor stub
}
/**
 * @param organizer
 * @param subject
 * @param startDate
 * @param endDate
 */
public Evenement(String organizer, String subject, Date startDate, Date endDate) {
	super();
	this.organizer = organizer;
	this.subject = subject;
	this.startDate = startDate;
	this.endDate = endDate;
}
/**
 * @return the organizer
 */
public String getOrganizer() {
	return organizer;
}
/**
 * @param organizer the organizer to set
 */
public void setOrganizer(String organizer) {
	this.organizer = organizer;
}
/**
 * @return the subject
 */
public String getSubject() {
	return subject;
}
/**
 * @param subject the subject to set
 */
public void setSubject(String subject) {
	this.subject = subject;
}
/**
 * @return the startDate
 */
public Date getStartDate() {
	return startDate;
}
/**
 * @param startDate the startDate to set
 */
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
/**
 * @return the endDate
 */
public Date getEndDate() {
	return endDate;
}
/**
 * @param endDate the endDate to set
 */
public void setEndDate(Date endDate) {
	this.endDate = endDate;
}


}
