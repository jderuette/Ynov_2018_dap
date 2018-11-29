package fr.ynov.dap.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Permet d'indiquer les différentes infos souhaitées lorsqu'on récupère un email.
 * @author abaracas
 *
 */
public class EmailAddressService {
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Address")
	private String address;
	/**
	 * @return the name
	 */
	public String getName() {
	    return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
	    return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
	    this.address = address;
	}
	
	
}
