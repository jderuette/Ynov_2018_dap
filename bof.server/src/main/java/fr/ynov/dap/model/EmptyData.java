package fr.ynov.dap.model;

public class EmptyData extends MasterModel {

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;

	public EmptyData(String message) {
		super();
		this.message = message;
	}
	
}
