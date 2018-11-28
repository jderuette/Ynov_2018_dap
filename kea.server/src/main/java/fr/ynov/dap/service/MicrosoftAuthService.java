package fr.ynov.dap.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.Config;

/**
 * The service to authenticate your account on microsoft API.
 * @author Antoine
 *
 */
@Service
public class MicrosoftAuthService {
  /**
   * the customConfig made in Config.java.
   */
  @Autowired
  private Config customConfig;
  /**
   * the list of scopes that defines which applications we can have access.
   */
  private String[] scopes = {"openid", "offline_access", "profile",
      "https://outlook.office.com/mail.read" };

  /**
   * Logger is the object we use to log important informations or report bugs.
   */
  private static final Logger LOGGER = (Logger) LogManager
      .getLogger(GoogleService.class);

  /**
   * Constructor.
   * @throws InstantiationException nothing special
   * @throws IllegalAccessException nothing special
   * @throws IOException nothing special
   */
  public MicrosoftAuthService()
      throws InstantiationException, IllegalAccessException, IOException {
    //init();
  }

  /**
   * get the list of scopes declared.
   * @return a list of microsoft scopes
   */
  private String getScopes() {
    StringBuilder sb = new StringBuilder();
    for (String scope : scopes) {
      sb.append(scope + " ");
    }
    return sb.toString().trim();
  }

  /**
   * the method used to add microsoft login/password to Config class.
   * @throws IOException nothing special
   */
  private void init() throws IOException {
    String authConfigFile = "auth.properties";
    InputStream authConfigStream = MicrosoftAuthService.class.getClassLoader()
        .getResourceAsStream(authConfigFile);

    if (authConfigStream != null) {
      Properties authProps = new Properties();
      try {
        authProps.load(authConfigStream);
        if (customConfig != null) {
          customConfig.setMicrosoftAppId(authProps.getProperty("appId"));
          customConfig
              .setMicrosoftAppPassword(authProps.getProperty("appPassword"));
          customConfig
              .setMicrosoftRedirectUrl(authProps.getProperty("redirectUrl"));
        } else {
          LOGGER.debug("the object custom config is null : autowired problem");
        }
      } finally {
        authConfigStream.close();
      }
    } else {
      throw new FileNotFoundException(
          "Property file '" + authConfigFile + "' not found in the classpath.");
    }
  }

  /**
   * get login URL for microsoft.
   * @param state UUID
   * @param nonce UUID
   * @return the URL where to redirect
   * @throws IOException nothing special
   */
  public String getLoginUrl(final UUID state, final UUID nonce) throws IOException {
    init();
    UriComponentsBuilder urlBuilder = UriComponentsBuilder
        .fromHttpUrl(customConfig.getMicrosoftAuthorizeUrl());
    urlBuilder.queryParam("client_id", customConfig.getMicrosoftAppId());
    urlBuilder.queryParam("redirect_uri",
        customConfig.getMicrosoftRedirectUrl());
    urlBuilder.queryParam("response_type", "code id_token");
    urlBuilder.queryParam("scope", getScopes());
    urlBuilder.queryParam("state", state);
    urlBuilder.queryParam("nonce", nonce);
    urlBuilder.queryParam("response_mode", "form_post");
    return urlBuilder.toUriString();
  }

}
