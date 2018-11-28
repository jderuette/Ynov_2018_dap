package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class OutlookEmailAddress.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookEmailAddress {
  
  /** The name. */
  private String name;
  
  /** The address. */
  private String address;

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }
  
  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Gets the address.
   *
   * @return the address
   */
  public String getAddress() {
    return address;
  }
  
  /**
   * Sets the address.
   *
   * @param address the new address
   */
  public void setAddress(String address) {
    this.address = address;
  }
}