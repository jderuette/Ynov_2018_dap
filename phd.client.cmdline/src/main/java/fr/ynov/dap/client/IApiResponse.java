package fr.ynov.dap.client;

import java.io.IOException;
/**
 * Interface implements methods onSuccess and onError
 * @author Dom
 */
public interface IApiResponse{
	/**
	 * Return the string when the request is onSuccess
	 * @param string
	 */
	void onSuccess(String string);
	
	/**
	 * Return the exception when the request is onError
	 * @param e
	 */
    void onError(IOException e);
}
