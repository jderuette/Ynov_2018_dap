package com.ynov.dap.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.dap.google.MailService;
import com.ynov.dap.models.MailModel;

/**
 * The Class MailController.
 */
@RestController
public class MailController {


    /** The mail service. */
    @Autowired
    private MailService mailService;

    /**
     * Gets the foos with header.
     *
     * @param gUser the g user
     * @return the foos with header
     */
    @RequestMapping(value = "/email/{gUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    //TODO bap by Djer Cohérent avec le nomage dans le controller.
    public ResponseEntity<MailModel> getFoosWithHeader(@PathVariable final String gUser) {
        try {
            return new ResponseEntity<MailModel>(mailService.getNbUnreadEmails(gUser), HttpStatus.ACCEPTED);
        } catch (IOException e) {
          //FIXME bap by Djer "e.printStackTrace();" affiche dans la console. Utilise un Logger !
            e.printStackTrace();
        } catch (Exception e) {
          //FIXME bap by Djer "e.printStackTrace();" affiche dans la console. Utilise un Logger !
            e.printStackTrace();
        }
        return new ResponseEntity<MailModel>(new MailModel(0), HttpStatus.BAD_REQUEST);
    }
}
