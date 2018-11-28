package fr.ynov.dap.data.interfaces;

import fr.ynov.dap.data.Contact;
import fr.ynov.dap.data.Event;
import fr.ynov.dap.data.Message;
import fr.ynov.dap.data.OutlookUser;
import fr.ynov.dap.data.PagedResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The Interface OutlookServiceInterface.
 */
public interface OutlookServiceInterface {

	/**
	 * Gets the current user.
	 *
	 * @return the current user
	 */
	@GET("/v1.0/me")
	Call<OutlookUser> getCurrentUser();

	/**
	 * Gets the messages.
	 *
	 * @param folderId   the folder id
	 * @param orderBy    the order by
	 * @param select     the select
	 * @param maxResults the max results
	 * @return the messages
	 */
	@GET("/v1.0/me/mailfolders/{folderid}/messages")
	Call<PagedResult<Message>> getMessages(@Path("folderid") String folderId, @Query("$orderby") String orderBy,
			@Query("$select") String select, @Query("$top") Integer maxResults);

	/**
	 * Gets the events.
	 *
	 * @param orderBy    the order by
	 * @param filter     the filter
	 * @param select     the select
	 * @param maxResults the max results
	 * @return the events
	 */
	@GET("/v1.0/me/events")
	Call<PagedResult<Event>> getEvents(@Query("$orderby") String orderBy, @Query("$filter") String filter,
			@Query("$select") String select, @Query("$top") Integer maxResults);

	/**
	 * Gets the contacts.
	 *
	 * @param orderBy    the order by
	 * @param select     the select
	 * @param maxResults the max results
	 * @return the contacts
	 */
	@GET("/v1.0/me/contacts")
	Call<PagedResult<Contact>> getContacts(@Query("$orderby") String orderBy, @Query("$select") String select,
			@Query("$top") Integer maxResults);
}