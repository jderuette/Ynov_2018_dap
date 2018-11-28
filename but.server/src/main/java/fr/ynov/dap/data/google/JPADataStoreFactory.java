package fr.ynov.dap.data.google;

import java.io.IOException;
import java.io.Serializable;

import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.data.AppUser;

/**
 * Factory of JPADataStore.
 * @author thibault
 *
 */
public class JPADataStoreFactory extends AbstractDataStoreFactory {
    /**
     * Owner of JPAStore.
     */
    private AppUser owner;

    /**
     * Repository of GoogleAccount.
     */
    private GoogleAccountRepository repository;

    /**
     * Constuctor of factory.
     * @param ownerUser Owner of JPAStore
     * @param gRepo            repository of google account
     */
    public JPADataStoreFactory(final GoogleAccountRepository gRepo, final AppUser ownerUser) {
        this.owner = ownerUser;
        this.repository = gRepo;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected final <V extends Serializable> DataStore<V> createDataStore(final String id) throws IOException {
        return (DataStore<V>) new JPADataStore(this, repository, id, owner);
    }
}
