package fr.ynov.dap.model.enumeration;

public enum GuestStatusEventEnum {

	ACCEPTED(1),

    TENTATIVE(2),

    DECLINED(3),

    NOT_ANSWERED(4),

    OWNER(5),

    UNKNOWN(6);

    private final Integer value;

    GuestStatusEventEnum(final Integer val) {
        this.value = val;
    }

    public Integer getValue() {
        return value;
}
}
