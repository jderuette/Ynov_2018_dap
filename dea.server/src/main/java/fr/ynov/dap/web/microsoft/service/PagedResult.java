
package fr.ynov.dap.web.microsoft.service;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 * @author antod
 *
 * @param <T>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedResult<T>
{
  /**
   * Variable nextPageLink
   */
  @JsonProperty("@odata.nextLink")
  private String nextPageLink;
  /**
   * Variable value
   */
  private T[] value;

  /**
   * Variabel count
   */
  @JsonProperty("@odata.count")
  private Integer count;

  /**
   * Récupère nextPageLink
   * 
   * @return
   */
  public String getNextPageLink()
  {
    return nextPageLink;
  }

  /**
   * Assigne nextPageLink
   * 
   * @param nextPageLink
   */
  public void setNextPageLink(String nextPageLink)
  {
    this.nextPageLink = nextPageLink;
  }

  /**
   * Récupèrevalue
   * 
   * @return
   */
  public T[] getValue()
  {
    return value;
  }

  /**
   * Assigne value
   * 
   * @param value
   */
  public void setValue(T[] value)
  {
    this.value = value;
  }

  /**
   * Récupèe count
   * 
   * @return
   */
  public Integer getCount()
  {
    return count;
  }

  /**
   * Assigne count
   * 
   * @param count
   */
  public void setCount(Integer count)
  {
    this.count = count;
  }
}
