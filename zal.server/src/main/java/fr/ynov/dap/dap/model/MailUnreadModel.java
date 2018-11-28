package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailUnreadModel {
	private Integer nbTotalEmailUnread;

	public Integer getNbTotalEmailUnread() {
		return nbTotalEmailUnread;
	}

	public void setNbTotalEmailUnread(Integer nbTotalEmailUnread) {
		this.nbTotalEmailUnread = nbTotalEmailUnread;
	}

	public MailUnreadModel(Integer nbTotalEmailUnread) {
		this.nbTotalEmailUnread = nbTotalEmailUnread;
	}
}
