package fr.ynov.dap.services;

import fr.ynov.dap.helpers.GoogleHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class PeopleService {

    //TODO grj by Djer IOC ! 
    private GoogleHelper googleHelper = new GoogleHelper();

    /**
     * Return the number of contacts
     *
     * @param userKey userKey to log
     * @return String number of contacts
     * @throws IOException              Exception
     * @throws GeneralSecurityException Exception
     */
    public final Map<String, Integer> getNumberContact(String userKey) throws IOException, GeneralSecurityException {

        Integer numberOfContact = googleHelper.getPeopleService(userKey).people().connections().list("people/me")
                .setPersonFields("names,emailAddresses").execute().getTotalPeople();

        Map<String, Integer> response = new HashMap<>();

        response.put("number_contact", numberOfContact);

        return response;
    }

}
