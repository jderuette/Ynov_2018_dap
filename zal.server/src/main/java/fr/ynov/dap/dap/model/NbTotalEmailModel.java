package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NbTotalEmailModel {
	private Integer nbTotalEmailUnread;
	
	public Integer getNbTotalEmailUnread() {
		return nbTotalEmailUnread;
	}

	public void setNbTotalEmailUnread(Integer nbTotalEmailUnread) {
		this.nbTotalEmailUnread = nbTotalEmailUnread;
	}

	public NbTotalEmailModel(Integer nbTotalEmailUnread){
		this.nbTotalEmailUnread = nbTotalEmailUnread;
	}
}
