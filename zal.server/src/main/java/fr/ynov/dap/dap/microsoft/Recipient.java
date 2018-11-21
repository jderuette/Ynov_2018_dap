package fr.ynov.dap.dap.microsoft;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.ynov.dap.dap.microsoft.entity.EmailAddress;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
  private EmailAddress emailAddress;

  public EmailAddress getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(EmailAddress emailAddress) {
    this.emailAddress = emailAddress;
  }
}