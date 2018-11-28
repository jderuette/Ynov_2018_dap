package fr.ynov.dap.model.enumeration;

public enum StatusEventEnum {
	 
    CONFIRMED(1),
    
    TENTATIVE(2),

    CANCELLED(3),

    UNKNOW(4);

    private final Integer value;

    StatusEventEnum(final Integer val) {
        this.value = val;
    }

    public Integer getValue() {
        return value;
}
}