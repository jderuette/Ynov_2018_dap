package fr.ynov.dap.client.model;

/**
 * This enumeration represent the attendee's status for an event.
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
     * @return Current value.s
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Transform integer to enum.
     * @param status Current status to be converted
     * @return New enum status
     */
    public static AttendeeEventStatusEnum getStatusFromInt(final Integer status) {
        for (AttendeeEventStatusEnum stat : AttendeeEventStatusEnum.values()) {
            if (stat.value == status) {
                return stat;
            }
        }
        return AttendeeEventStatusEnum.UNKNOWN;
    }

}
