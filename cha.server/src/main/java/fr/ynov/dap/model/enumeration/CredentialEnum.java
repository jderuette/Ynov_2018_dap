package fr.ynov.dap.model.enumeration;

/**
 * The Enum CredentialEnum.
 */
public enum CredentialEnum {

    /**
     * Credential from Google Api.
     */
    GOOGLE(1),

    /**
     * Credential from Microsoft Api.
     */
    MICROSOFT(2);

    /**
     * Current value.
     */
    private final Integer value;

    /**
     * Default constructor.
     * @param val status value.
     */
    CredentialEnum(final Integer val) {
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
