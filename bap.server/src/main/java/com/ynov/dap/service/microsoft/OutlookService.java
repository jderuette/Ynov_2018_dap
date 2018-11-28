package com.ynov.dap.service.microsoft;

import com.ynov.dap.model.microsoft.Contact;
import com.ynov.dap.model.microsoft.Event;
import com.ynov.dap.model.microsoft.Folder;
import com.ynov.dap.model.microsoft.Message;
import com.ynov.dap.model.microsoft.OutlookUser;
import com.ynov.dap.model.microsoft.PagedResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The Interface OutlookService.
 */
public interface OutlookService {

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
     * Gets the events.
     *
     * @param orderBy    the order by
     * @param select     the select
     * @param maxResults the max results
     * @return the events
     */
    @GET("/v1.0/me/events")
    Call<PagedResult<Event>> getEvents(@Query("$orderby") String orderBy, @Query("$select") String select,
            @Query("$top") Integer maxResults);

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

    /**
     * Gets the folder.
     *
     * @param folderName the folder name
     * @return the folder
     */
    @GET("/v1.0/me/mailFolders/{folderName}")
    Call<Folder> getFolder(@Path("folderName") String folderName);

    /**
     * Gets the nb contacts.
     *
     * @return the nb contacts
     */
    @GET("/v1.0/me/contacts?$count=true")
    Call<PagedResult<Contact>> getNbContacts();
}