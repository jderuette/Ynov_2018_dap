package fr.ynov.dap.services.microsoft;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * interface des services microsoft outlook.
 * @author alexa
 *
 */
public interface OutlookService {

  /**
   * récupère l'utilisateur actuellement connecté.
   * @return OutlookUser
   */
  @GET("/v1.0/me")
  Call<OutlookUser> getCurrentUser();

  /**
   * récupère les mais d'un dossier de la boite mail du compte microsoft.
   * @param folderId Id
   * @param orderBy orderby
   * @param select select
   * @param maxResults results
   * @return PagedResult Message
   */
  @GET("/v1.0/me/mailfolders/{folderid}/messages")
  Call<PagedResult<Message>> getMessages(
    @Path("folderid") String folderId,
    @Query("$orderby") String orderBy,
    @Query("$select") String select,
    @Query("$top") Integer maxResults
  );

  /**
   * récupère les évènements du calendrier du compte microsoft connecté.
   * @param orderBy orderby
   * @param select select
   * @param maxResults nb max résultat
   * @return PagedResult Event
   */
  @GET("/v1.0/me/events")
  Call<PagedResult<Event>> getEvents(
        @Query("$orderby") String orderBy,
        @Query("$select") String select,
        @Query("$top") Integer maxResults
  );
  /**
   * récupère les contacts du compts microsoft connecté.
   * @param orderBy orderby
   * @param select select
   * @param maxResults nb max résultat
   * @return PagedResult Contact
   */
  @GET("/v1.0/me/contacts")
  Call<PagedResult<Contact>> getContacts(
      @Query("$orderby") String orderBy,
      @Query("$select") String select,
      @Query("$top") Integer maxResults
  );
}
