package fr.ynov.dap.microsoft;

/**
 * Final class that contains eery scopes manage by Microsoft API.
 * @author Kévin Sibué
 *
 */
public final class MicrosoftScopes {

    /**
     * Default constructor.
     */
    private MicrosoftScopes() {

    }

    /**
     * OpenId Scope.
     */
    public static final String OPEN_ID = "openid";

    /**
     * Offline access Scope.
     */
    public static final String OFFLINE_ACCESS = "offline_access";

    /**
     * Profile Scope.
     */
    public static final String PROFILE = "profile";

    /**
     * User Read Scope.
     */
    public static final String USER_READ = "User.Read";

    /**
     * Mail Read Scope.
     */
    public static final String MAIL_READ = "Mail.Read";

    /**
     * Mail Read / Write Scope.
     */
    public static final String MAIL_READ_WRITE = "mail.readwrite";

    /**
     * Calendar Read scope.
     */
    public static final String CALENDARS_READ = "Calendars.Read";

    /**
     * Contact Read scope.
     */
    public static final String CONTACTS_READ = "Contacts.Read";
}
