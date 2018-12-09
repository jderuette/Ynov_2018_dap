package fr.ynov.dap.data.google;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.Lists;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStore;
import com.google.common.base.Optional;

import fr.ynov.dap.data.AppUser;

/**
 * JPADataStore to save data in database.
 * @author thibault
 *
 */
public class JPADataStore extends AbstractDataStore<StoredCredential> {
    /**
     * Factory of JPAStore.
     */
    private JPADataStoreFactory jpaDataStoreFactory;

    /**
     * Owner user of this JPAStore.
     */
    private AppUser owner;

    /**
     * Repository of GoogleAccount.
     */
    private GoogleAccountRepository repository;

    /**
     * Logger for the class.
     */
    //TODO but by Djer |Log4J| Devrait être static final. Une instance de cette classe est construite régulièrement (et contruire un Logger est couteux).
    private static Logger logger = LogManager.getLogger();

    /**
     * @param dataStoreFactory data store factory
     * @param id               data store ID
     * @param gRepo            repository of google account
     * @param userOwner        owner of storeId
     */
    protected JPADataStore(final JPADataStoreFactory dataStoreFactory, final GoogleAccountRepository gRepo,
            final String id, final AppUser userOwner) {
        super(dataStoreFactory, id);
        this.repository = gRepo;
        this.owner = userOwner;
    }

    @Override
    public final JPADataStoreFactory getDataStoreFactory() {
        return jpaDataStoreFactory;
    }

    @Override
    public final int size() throws IOException {
        return (int) repository.count();
    }

    @Override
    public final boolean isEmpty() throws IOException {
        return size() == 0;
    }

    @Override
    public final boolean containsKey(final String key) throws IOException {
        return repository.existsByAccountNameAndOwner(key, owner);
    }

    @Override
    public final boolean containsValue(final StoredCredential value) throws IOException {
        return repository.existsByAccessToken(value.getAccessToken());
    }

    @Override
    public final Set<String> keySet() throws IOException {
        return repository.findAllAccountNamesByOwner(owner);
    }

    @Override
    public final Collection<StoredCredential> values() throws IOException {
        return Lists.newArrayList(repository.findAll()).stream().map(c -> {
            StoredCredential credential = new StoredCredential();
            credential.setAccessToken(c.getAccessToken());
            credential.setRefreshToken(c.getRefreshToken());
            credential.setExpirationTimeMilliseconds(c.getExpirationTimeMilliseconds());
            return credential;
        }).collect(Collectors.toList());
    }

    @Override
    public final StoredCredential get(final String key) throws IOException {
        Optional<GoogleAccount> jpaStoredCredentialOptional = repository.findByAccountNameAndOwner(key, owner);
        if (!jpaStoredCredentialOptional.isPresent()) {
            //TODO but by Djer |POO| Evite les multiples return dans une même méthode
            return null;
        }
        GoogleAccount googleCredential = jpaStoredCredentialOptional.get();
        StoredCredential credential = new StoredCredential();
        credential.setAccessToken(googleCredential.getAccessToken());
        credential.setRefreshToken(googleCredential.getRefreshToken());
        credential.setExpirationTimeMilliseconds(googleCredential.getExpirationTimeMilliseconds());
        return credential;
    }

    @Override
    public final DataStore<StoredCredential> set(final String key, final StoredCredential value) throws IOException {
        //TODO but by Djer |POO| Tu devrais appeler cette variable "googleAccount" c'est confusant ce "credential" surtout dans cette classe
        GoogleAccount googleCredential = repository.findByAccountNameAndOwner(key, owner)
                .or(new GoogleAccount(key, owner, value));
        //TODO but by Djer |POO| Ne semble pas utile le constructeur que tu utilise juste au dessus fait déja le travail de "apply"
        googleCredential.apply(value);
        logger.info("User : " + owner.getUserKey());
        repository.save(googleCredential);
        return this;
    }

    @Override
    public final DataStore<StoredCredential> clear() throws IOException {
        //TODO but by Djer |POO| Attention tu ne respecte pas l'API de "DataStore" tu  devrais effacer TOUS les credential (je trouve cette méthode un peu "violente", mais c'est ce que demande l'API "Deletes all of the stored keys and values.")
        repository.deleteByOwner(owner);
        return this;
    }

    @Override
    public final DataStore<StoredCredential> delete(final String key) throws IOException {
        repository.deleteByAccountNameAndOwner(key, owner);
        return this;
    }
}
