package fr.ynov.dap.client;

import java.net.URI;

//TODO for by Djer même les classe "contenant que des static" doivent commencer par une majuscule !
public class link {
	public static final URI ADD_ACCOUNT = URI.create("http://localhost:8080/account/add/");
	public static final URI ADD_GOOGLE_ACCOUNT = URI.create("http://localhost:8080/account/add/");
	public static final URI NEXT_EVENT = URI.create("http://localhost:8080/nextEvent?userKey=");
	public static final URI NB_UNREAD_MAIL = URI.create("http://localhost:8080/email/nbUnread?userKey=");
	public static final URI NB_CONTACT = URI.create("http://localhost:8080/nbContact?userKey=");
	public static final URI LABEL_NAME = URI.create("http://localhost:8080/email/getLabels?userKey=");
	public static final URI MICROSOFT_LOGIN = URI.create("http://localhost:8080/account/add/microsoft/{accountName}?userKey=");
	public static final URI MICROSOFT_MAIL = URI.create("http://localhost:8080/mail");
	
}
