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
public class MailController extends BaseController {


    /** The mail service. */
    @Autowired
    private MailService mailService;

    /**
     * Gets the nb unread.
     *
     * @param gUser the g user
     * @return the nb unread
     */
    @RequestMapping(value = "/email/{gUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MailModel> getNbUnread(@PathVariable final String gUser) {
        try {
            return new ResponseEntity<MailModel>(mailService.getNbUnreadEmails(gUser), HttpStatus.ACCEPTED);
        } catch (IOException e) {
            getLogger().error(e.getMessage());
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }
        return new ResponseEntity<MailModel>(new MailModel(0), HttpStatus.BAD_REQUEST);
    }

    /* (non-Javadoc)
     * @see com.ynov.dap.controllers.BaseController#getClassName()
     */
    @Override
    public String getClassName() {
        return MailController.class.getName();
    }
}
