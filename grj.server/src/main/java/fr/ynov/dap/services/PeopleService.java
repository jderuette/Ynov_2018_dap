package fr.ynov.dap.services;

import fr.ynov.dap.helpers.GoogleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * PeopleService
 */
@Service
public class PeopleService {

    @Autowired
    private GoogleHelper googleHelper;

    /**
     * Return the number of contacts
     *
     * @param googleAccountName userKey to log
     * @return String number of contacts
     * @throws IOException              Exception
     * @throws GeneralSecurityException Exception
     */
    public int getNumberContact(String googleAccountName) throws IOException, GeneralSecurityException {

        return googleHelper.getPeopleService(googleAccountName).people().connections().list("people/me")
                .setPersonFields("names,emailAddresses").execute().getTotalPeople();

    }

}
