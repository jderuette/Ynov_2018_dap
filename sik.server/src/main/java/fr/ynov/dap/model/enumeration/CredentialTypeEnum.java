package fr.ynov.dap.model.enumeration;

/**
 * This enum allow to describe a credential.
 * @author Kévin Sibué
 *
 */
public enum CredentialTypeEnum {

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
    CredentialTypeEnum(final Integer val) {
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
