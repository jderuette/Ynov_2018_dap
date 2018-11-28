package fr.ynov.dap.services.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Outlook entity used by the Microsoft API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookUser {
    /**
     * Identifier.
     */
    private String id;
    /**
     * E-mail.
     */
    private String mail;
    /**
     * Display name.
     */
    private String displayName;

    /**
     * Id getter.
     * @return identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Id setter.
     * @param i id
     */
    public void setId(final String i) {
        this.id = i;
    }

    /**
     * Mail getter.
     * @return email.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Mail setter.
     * @param email email address.
     */
    public void setMail(final String email) {
        this.mail = email;
    }

    /**
     * Display name getter.
     * @return display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Display name setter.
     * @param name display name.
     */
    public void setDisplayName(final String name) {
        this.displayName = name;
    }
}
