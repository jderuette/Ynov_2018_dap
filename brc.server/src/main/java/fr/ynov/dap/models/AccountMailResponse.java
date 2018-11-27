package fr.ynov.dap.models;

import java.util.ArrayList;

import fr.ynov.dap.microsoft.models.Message;

public class AccountMailResponse {

	private String name;
	private ArrayList<Message> mails;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Message> getMails() {
		return mails;
	}
	public void setMails(ArrayList<Message> mails) {
		this.mails = mails;
	}
	
	
}
