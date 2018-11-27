package fr.ynov.dap.utils;

import org.json.JSONObject;

/**
 * The Class JSONResponse.
 */
public class JSONResponse {
	
	/**
	 * Response object.
	 *
	 * @param field the field
	 * @param message the message
	 * @return the JSON object
	 */
	public static JSONObject responseObject(String field, String message) {
		JSONObject response = new JSONObject();
    	response.put(field, message);
    	return response;
	}
	
	/**
	 * Response string.
	 *
	 * @param field the field
	 * @param message the message
	 * @return the string
	 */
	public static String responseString(String field, String message) {
		JSONObject response = new JSONObject();
    	response.put(field, message);
    	return response.toString();
	}
	
	/**
	 * Response string.
	 *
	 * @param field the field
	 * @param message the message
	 * @return the string
	 */
	public static String responseString(String field, Integer message) {
		JSONObject response = new JSONObject();
    	response.put(field, message);
    	return response.toString();
	}

	public static JSONObject responseString(String field, JSONObject jsonObject) {
		JSONObject response = new JSONObject();
    	response.put(field, jsonObject);
		return response;
	}
}
