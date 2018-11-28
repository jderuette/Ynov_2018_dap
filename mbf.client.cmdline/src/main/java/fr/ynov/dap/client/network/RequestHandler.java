package fr.ynov.dap.client.network;

/**
 * This interface allows us to handle separately the success and failure server responses.
 */
public interface RequestHandler {
    /**
     * This is the success callback.
     * @param response The request response
     */
    void uponSuccess(String response);

    /**
     * This is the error callback.
     * @param e The error exception
     */
    void uponError(Exception e);
}
