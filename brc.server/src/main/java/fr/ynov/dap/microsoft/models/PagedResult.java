package fr.ynov.dap.microsoft.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class PagedResult.
 *
 * @param <T> the generic type
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedResult<T> {
  
  /** The next page link. */
  @JsonProperty("@odata.nextLink")
  private String nextPageLink;
  
  /** The value. */
  private T[] value;

  /**
   * Gets the next page link.
   *
   * @return the next page link
   */
  public String getNextPageLink() {
    return nextPageLink;
  }
  
  /**
   * Sets the next page link.
   *
   * @param nextPageLink the new next page link
   */
  public void setNextPageLink(String nextPageLink) {
    this.nextPageLink = nextPageLink;
  }
  
  /**
   * Gets the value.
   *
   * @return the value
   */
  public T[] getValue() {
    return value;
  }
  
  /**
   * Sets the value.
   *
   * @param value the new value
   */
  public void setValue(T[] value) {
    this.value = value;
  }
}