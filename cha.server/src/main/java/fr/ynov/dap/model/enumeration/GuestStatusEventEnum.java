package fr.ynov.dap.model.enumeration;

public enum GuestStatusEventEnum {
	/**
	 * Confirmed status.
	 * The attendee has accepted the invitation.
	 */
	ACCEPTED(1),

    /**
     * Tentative status.
     * The attendee has tentatively accepted the invitation.
     */
    TENTATIVE(2),

    /**
     * Cancelled status.
     * The attendee has declined the invitation.
     */
    DECLINED(3),

    /**
     * Need action status.
     * The attendee has not responded to the invitation.
     */
    NOT_ANSWERED(4),

    /**
     * Owner status.
     * Not provided by Google Api.
     * Returned when the event own by the user
     */
    OWNER(5),

    /**
     * Unknown status.
     */
    UNKNOWN(6);

    /**
     * Current value.
     */
    private final Integer value;

    /**
     * Default constructor.
     * @param val status value.
     */
    GuestStatusEventEnum(final Integer val) {
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
