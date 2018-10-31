package fr.ynov.dap.dap.model;

public class ServerException extends MasterModel {


	
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public ServerException(String message) {
		this.message = message;
	}
}
