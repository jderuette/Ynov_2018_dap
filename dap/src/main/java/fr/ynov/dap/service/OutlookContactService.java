package fr.ynov.dap.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Permet de gérer la récupération des contacts microsoft, avec leurs différents champs
 * @author abaracas
 *
 */
//TODO baa by Djer |SOA| Cette classe n'est pas un service
public class OutlookContactService {
	@JsonProperty("Id")
	private String id;
	@JsonProperty("GivenName")
	private String givenName;
	@JsonProperty("Surname")
	private String surname;
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("EmailAddresses")
	private EmailAddressService[] emailAddresses;
	/**
	 * @return the id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * @return the givenName
	 */
	public String getGivenName() {
	    return givenName;
	}
	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
	    this.givenName = givenName;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
	    return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
	    this.surname = surname;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
	    return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
	    this.companyName = companyName;
	}
	/**
	 * @return the emailAddresses
	 */
	public EmailAddressService[] getEmailAddresses() {
	    return emailAddresses;
	}
	/**
	 * @param emailAddresses the emailAddresses to set
	 */
	public void setEmailAddresses(EmailAddressService[] emailAddresses) {
	    this.emailAddresses = emailAddresses;
	}
	
	
}
