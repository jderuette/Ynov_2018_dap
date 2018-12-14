package fr.ynov.dap.dap.service.microsoft.requestservice;

//TODO zal by DJer |IDE| configure les "save action" de ton IDE pour qu'il organise les import (et format ton code) lorsque tu sauvegardes
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.data.microsoft.Message;
import fr.ynov.dap.dap.data.microsoft.PagedResult;
import fr.ynov.dap.dap.data.microsoft.model.OutlookContact;
import fr.ynov.dap.dap.data.microsoft.model.OutlookEvent;
import fr.ynov.dap.dap.data.microsoft.model.OutlookFolder;
import fr.ynov.dap.dap.data.microsoft.model.OutlookUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The Interface OutlookRequestService.
 */
public interface OutlookRequestService {

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
	 * @param folderId
	 *            the folder id
	 * @param orderBy
	 *            the order by
	 * @param select
	 *            the select
	 * @param maxResults
	 *            the max results
	 * @return the messages
	 */
	@GET("/v1.0/me/mailfolders/{folderid}/messages")
	Call<PagedResult<Message>> getMessages(@Path("folderid") String folderId, @Query("$orderby") String orderBy,
			@Query("$select") String select, @Query("$top") Integer maxResults);

	/**
	 * Gets the events.
	 *
	 * @param orderBy
	 *            the order by
	 * @param select
	 *            the select
	 * @param maxResults
	 *            the max results
	 * @param filter
	 *            the filter
	 * @return the events
	 */
	@GET("/v1.0/me/events")
	Call<PagedResult<OutlookEvent>> getEvents(@Query("$orderby") String orderBy, @Query("$select") String select,
			@Query("$top") Integer maxResults, @Query("$filter") String filter);

	/**
	 * Gets the contacts.
	 *
	 * @param orderBy
	 *            the order by
	 * @param select
	 *            the select
	 * @return the contacts
	 */
	@GET("/v1.0/me/contacts")
	Call<PagedResult<OutlookContact>> getContacts(@Query("$orderby") String orderBy, @Query("$select") String select);

	/**
	 * Gets the outlook folder.
	 *
	 * @param folder
	 *            the folder
	 * @return the outlook folder
	 */
	@GET("/v1.0/me/mailFolders/{folder}")
	Call<OutlookFolder> getOutlookFolder(@Path("folder") String folder);
}