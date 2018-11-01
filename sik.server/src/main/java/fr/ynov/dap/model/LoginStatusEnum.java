package fr.ynov.dap.model;

/**
 * Represent login status.
 * @author Kévin Sibué
 *
 */
public enum LoginStatusEnum {

    /**
     * User account already added.
     */
    ALREADY_ADDED(1),

    /**
     * Error occurred when user try to login.
     */
    ERROR(2),

    /**
     * User auth success, you must be redirected.
     */
    REDIRECTION(3);

    /**
     * Current value.
     */
    private final Integer value;

    /**
     * Default constructor.
     * @param val status value.
     */
    LoginStatusEnum(final Integer val) {
        this.value = val;
    }

    /**
     * Get current value.
     * @return Current value.
     */
    public Integer getValue() {
        return value;
    }

}
