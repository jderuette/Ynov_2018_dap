
package fr.ynov.dap.web.microsoft.service;


import fr.ynov.dap.microsoft.Contact;
import fr.ynov.dap.microsoft.Event;
import fr.ynov.dap.microsoft.Folder;
import fr.ynov.dap.microsoft.Message;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Interface OutlookService
 * 
 * @author antod
 *
 */
public interface OutlookService
{
  /**
   * Récupère l'utilisateur actuel
   * 
   * @return
   */
  @GET("/v1.0/me")
  Call<OutlookUser> getCurrentUser();

  /**
   * Récupère les messages, et applique $folderid, $orderby, $select et $top
   * 
   * @param folderId
   * @param orderBy
   * @param select
   * @param maxResults
   * @return
   */
  @GET("/v1.0/me/mailfolders/{folderid}/messages")
  Call<PagedResult<Message>> getMessages(@Path("folderid") String folderId, @Query("$orderby") String orderBy,
      @Query("$select") String select, @Query("$top") Integer maxResults);

  /**
   * Récupère la boite des mails reçus
   * 
   * @return
   */
  @GET("/v1.0/me/Mailfolders/inbox")
  Call<Folder> getInboxFolder();

  /**
   * Récupère les events
   * 
   * @param orderBy
   * @param select
   * @param maxResults
   * @param filter
   * @return
   */
  @GET("/v1.0/me/events")
  Call<PagedResult<Event>> getEvents(@Query("$orderby") String orderBy, @Query("$select") String select,
      @Query("$top") Integer maxResults, @Query("$filter") String filter);

  /**
   * Récupères les contacts, et applique $orderby, $select et $top
   * 
   * @param orderBy
   * @param select
   * @param maxResults
   * @return
   */
  @GET("/v1.0/me/contacts")
  Call<PagedResult<Contact>> getContacts(@Query("$orderby") String orderBy, @Query("$select") String select,
      @Query("$top") Integer maxResults);

  /**
   * Récupère les contacts
   * 
   * @return
   */
  @GET("/v1.0/me/contacts?$count=true")
  Call<PagedResult<Contact>> getContacts();
}
