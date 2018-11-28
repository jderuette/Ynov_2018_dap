
package fr.ynov.dap.web.microsoft.service;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Classe EmailAddress pour microsoft
 * 
 * @author antod
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAddress
{
  /**
   * Variable name
   */
  private String name;
  /**
   * Variable adress
   */
  private String address;

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return the address
   */
  public String getAddress()
  {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address)
  {
    this.address = address;
  }

}
