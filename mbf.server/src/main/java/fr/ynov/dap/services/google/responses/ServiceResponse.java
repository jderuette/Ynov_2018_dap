package fr.ynov.dap.services.google.responses;

/**
 * This is a generic class which handles all the responses of the RestApi
 * @param <T> The data to be returned. T is the generic type.
 */
public class ServiceResponse<T> {
    /**
     * The response message in case of an error or empty results. Default value is null.
     */
    private String message = null;
    /**
     * The data response to be returned to the client. Default value is null.
     */
    private T data = null;

    /**
     *  This methods returns the data of the request
     * @return The data of the request.
     */
    public final T getData() {
        return data;
    }

    /**
     *  This methods sets the data of the request to the data attribute of the class.
=     */
    public final void setData(T data) {
        this.data = data;
    }

    /**
     *  This methods returns the message of the request.
     * @return The message of the request.
     */
    public final String getMessage() {
        return message;
    }

    /**
     *  This methods sets the message of the request to the message attribute of the class.
     */
    public final void setMessage(String msg) {
        this.message = msg;
    }

}
