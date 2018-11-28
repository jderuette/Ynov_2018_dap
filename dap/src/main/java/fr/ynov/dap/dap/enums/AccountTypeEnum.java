package fr.ynov.dap.dap.enums;

/**
 *
 * @author David_tepoche
 *
 */
public enum AccountTypeEnum {

    GOOGLE("google") , MICROSOFT("microsoft");

    /**
     * the name of the enum.
     */
    private String name;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param nameOfEnum contruct of the enum
     */
    AccountTypeEnum(final String nameOfEnum) {
        this.name = nameOfEnum;
    }
}
