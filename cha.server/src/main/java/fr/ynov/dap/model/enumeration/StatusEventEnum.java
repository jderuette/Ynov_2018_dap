package fr.ynov.dap.model.enumeration;

public enum StatusEventEnum {
	 
	/**
     * Confirmed status.
     */
    CONFIRMED(1),
    
	/**
     * Tentative status.
     */
    TENTATIVE(2),

    /**
     * Cancelled status.
     */
    CANCELLED(3),

    /**
     * Unknow status.
     */
    UNKNOW(4);

    /**
     * Current value.
     */
    private final Integer value;

    /**
     * Default constructor.
     * @param val status value.
     */
    StatusEventEnum(final Integer val) {
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