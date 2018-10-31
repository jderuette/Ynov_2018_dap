package fr.ynov.dap.dap.Models;

import java.util.Date;

public class CustomEvent {
	public CustomEvent(Date start, Date end, String subject, String confirmed) {
		super();
		this.start = start;
		this.end = end;
		this.subject = subject;
		this.confirmed = confirmed;
	}
	Date start;
	Date end;
	String subject;
	String confirmed;
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}
}
