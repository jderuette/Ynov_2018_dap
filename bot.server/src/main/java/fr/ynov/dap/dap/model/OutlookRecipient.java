package fr.ynov.dap.dap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class OutlookRecipient.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookRecipient {
  
  /** The email address. */
  private OutlookEmailAddress emailAddress;

  /**
   * Gets the email address.
   *
   * @return the email address
   */
  public OutlookEmailAddress getEmailAddress() {
    return emailAddress;
  }

  /**
   * Sets the email address.
   *
   * @param emailAddress the new email address
   */
  public void setEmailAddress(OutlookEmailAddress emailAddress) {
    this.emailAddress = emailAddress;
  }
}