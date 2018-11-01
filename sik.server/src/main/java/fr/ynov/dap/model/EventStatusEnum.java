package fr.ynov.dap.model;

/**
 * This enumeration represent the status of an event.
 * @author Kévin Sibué
 *
 */
public enum EventStatusEnum {

    /**
     * Tentative status.
     */
    TENTATIVE(1),

    /**
     * Cancelled status.
     */
    CANCELLED(2),

    /**
     * Confirmed status.
     */
    CONFIRMED(3),

    /**
     * Unknow status.
     */
    UNKNOW(99);

    /**
     * Current value.
     */
    private final Integer value;

    /**
     * Default constructor.
     * @param val status value.
     */
    EventStatusEnum(final Integer val) {
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
