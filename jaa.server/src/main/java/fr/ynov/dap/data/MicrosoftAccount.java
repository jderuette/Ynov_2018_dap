package fr.ynov.dap.data;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ynov.dap.microsoft.auth.IdToken;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * @author adrij
 *
 */
@Entity
public class MicrosoftAccount {

    /**
     * Length of the string to save on the database.
     * It's used to specify the length of long string, like the authCode.
     */
    //TODO jaa by Djer |JPA| Cumulée tes colonnes sont trop grande pour mon MySQL, et si je reduit les données ne rentre plus. Utilise un BLOB, ou stocke dans uen autre table (OneToOne avec un @Entity)
    private static final int STRING_LENGTH = 8192;

    /**
     * Microsoft Account Id.
     */
    @Id
    @GeneratedValue
    //TODO jaa by Djer |IDE| Ton ide t'indique que ca n'est pas utilisé. Tu n'as pas créer les getter/setters
    private Integer id;

    /**
     * Owner of this MicrosoftAccount.
     */
    @ManyToOne
    private AppUser appUser;

    /**
     * Get the owner.
     * @return the owner of this MicrosoftAccount.
     */
    public AppUser getOwner() {
        return this.appUser;
    }

    /**
     * Set the owner of this MicrosoftAccount.
     * @param user AppUser (owner) of this MicrosoftAccount.
     */
    public void setOwner(final AppUser user) {
        this.appUser = user;
    }

    /**
     * Name of the account.
     */
    @Column
    private String accountName;

    /**
     * Getter of AccountName.
     * @return account name.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Setter of AccountName.
     * @param name AccountName
     */
    public void setAccountName(final String name) {
        this.accountName = name;
    }

    /**
     * TokenResponse from the Microsoft API.
     */
    @Column(length = STRING_LENGTH)
    //TODO jaa by Djer |JPA| Tu devrais "transformer" TokenResponse en entité (ajouter un @entity, et configure les attributs nécéssaire avec un @Column, @Id,...). Tu aurais ainsi des données structurée dans ta BDD
    private String tokenResponse;

    /**
     * Getter of the TokenResponse.
     * @return the Microsoft token response.
     * @throws JsonParseException exception if the parse fails.
     * @throws JsonMappingException exception if the mapping fails.
     * @throws IOException exception if the stored value is incorrect.
     */
    public TokenResponse getTokenResponse() throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(tokenResponse, TokenResponse.class);
    }

    /**
     * Setter of TokenResponse.
     * @param token tokenResponse to store.
     * @throws JsonProcessingException exception if the serialization fails.
     */
    public void setTokenResponse(final TokenResponse token) throws JsonProcessingException {
        this.tokenResponse = (String) new ObjectMapper().writeValueAsString(token);
    }

    /**
     * IdToken from the Microsoft API.
     */
    @Column(length = STRING_LENGTH)
    //TODO jaa by Djer |JPA| le IdToken est est très temporaire, ca ne parait pas utile de le stocker. Si vraiment tu souhaites le stocker, transforme "IdToken" en entity. SI tu le stock, il faut que tu le mette à jour à chaque fois que nécéssaire (mais ca n'est pas très utile à priori)
    private String idToken;

    /**
     * IdToken getter.
     * @return the token id.
     * @throws JsonParseException exception if the parse fails.
     * @throws JsonMappingException exception if the mapping fails.
     * @throws IOException exception if the stored value is incorrect.
     */
    public IdToken getIdToken() throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(idToken, IdToken.class);
    }

    /**
     * IdToken setter.
     * @param idTok IdToken.
     * @throws JsonProcessingException exception if the serialization fails.
     */
    public void setIdToken(final IdToken idTok) throws JsonProcessingException {
        //TODO jaa by Djer |IDE| Ton IDE t'indique que ca n'est pas utilisé. Bug ? A supprimer ?
        String tokenTest = (String) new ObjectMapper().writeValueAsString(idTok);
        this.idToken = (String) new ObjectMapper().writeValueAsString(idTok);
    }

    /**
     * Default Constructor.
     */
    public MicrosoftAccount() {
    }
}
