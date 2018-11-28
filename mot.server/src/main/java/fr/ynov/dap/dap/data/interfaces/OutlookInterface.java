package fr.ynov.dap.dap.data.interfaces;

import fr.ynov.dap.dap.data.microsoft.Contact;
import fr.ynov.dap.dap.data.microsoft.EventMicrosoft;
import fr.ynov.dap.dap.data.microsoft.Message;
import fr.ynov.dap.dap.data.microsoft.OutlookUser;
import fr.ynov.dap.dap.data.microsoft.PagedResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The Interface OutlookInterface.
 */
public interface OutlookInterface {

	@GET("/v1.0/me")
	Call<OutlookUser> getCurrentUser();

	/**
	 * Gets the messages.
	 *
	 * @param folderId the folder id
	 * @param orderBy the order by
	 * @param select the select
	 * @param maxResults the max results
	 * @return the messages
	 */
	@GET("/v1.0/me/mailfolders/{folderid}/messages")
	Call<PagedResult<Message>> getMessages(@Path("folderid") String folderId, @Query("$orderby") String orderBy,
			@Query("$select") String select, @Query("$top") Integer maxResults);

	/**
	 * Gets the events.
	 *
	 * @param orderBy the order by
	 * @param filter the filter
	 * @param select the select
	 * @param maxResults the max results
	 * @return the events
	 */
	@GET("/v1.0/me/events")
	Call<PagedResult<EventMicrosoft>> getEvents(@Query("$orderby") String orderBy, @Query("$filter") String filter,
			@Query("$select") String select, @Query("$top") Integer maxResults);

	/**
	 * Gets the contacts.
	 *
	 * @param orderBy the order by
	 * @param select the select
	 * @param maxResults the max results
	 * @return the contacts
	 */
	@GET("/v1.0/me/contacts")
	Call<PagedResult<Contact>> getContacts(@Query("$orderby") String orderBy, @Query("$select") String select,
			@Query("$top") Integer maxResults);
}
