package fr.ynov.dap.model.enumeration;

/**
 * This enumeration represent the attendee's status of an event.
 * @author Kévin Sibué
 *
 */
public enum AttendeeEventStatusEnum {

    /**
     * Tentative status.
     * The attendee has tentatively accepted the invitation.
     */
    TENTATIVE(1),

    /**
     * Cancelled status.
     * The attendee has declined the invitation.
     */
    DECLINED(2),

    /**
     * Confirmed status.
     * The attendee has accepted the invitation.
     */
    ACCEPTED(3),

    /**
     * Need action status.
     * The attendee has not responded to the invitation.
     */
    NEEDS_ACTION(4),

    /**
     * Owner status.
     * Not provided by Google Api.
     * Returned when the event own by the user
     */
    OWNER(5),

    /**
     * Unknown status.
     */
    UNKNOWN(99);

    /**
     * Current value.
     */
    private final Integer value;

    /**
     * Default constructor.
     * @param val status value.
     */
    AttendeeEventStatusEnum(final Integer val) {
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
