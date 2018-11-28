package fr.ynov.dap.model.enumeration;

public enum CredentialEnum {

    GOOGLE(1),

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
