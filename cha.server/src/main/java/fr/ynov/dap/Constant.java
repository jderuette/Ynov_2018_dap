package fr.ynov.dap;

public class Constant {
	/**
     * Represent user id on session attributes.
     */
    public static final String SESSION_USER_ID = "userKey";

    /**
     * Represent account name on session attributes.
     */
    public static final String SESSION_ACCOUNT_NAME = "accountName";

    /**
     * Represent account name on session attributes.
     */
    public static final String SESSION_EXPECTED_STATE = "expected_state";

    /**
     * Represent account name on session attributes.
     */
    public static final String SESSION_EXPECTED_NONCE = "expected_nonce";

    /**
     * Represent size of token for database.
     */
    public static final int DATABASE_TOKEN_SIZE = 5000;

    /**
     * Represent value to convert second to millisecond.
     */
    public static final int SECOND_TO_MILLISECOND = 1000;

    /**
     * Represent the number of email to load.
     */
    public static final int NUMBER_OF_MAILS = 10;
}
