package com.ynov.dap.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.dap.model.MailModel;
import com.ynov.dap.service.google.GoogleMailService;
import com.ynov.dap.service.microsoft.MicrosoftMailService;

/**
 * The Class MailController.
 */
@RestController
@RequestMapping("mail/unread")
public class MailController extends BaseController {

    /** The google mail service. */
    @Autowired
    private GoogleMailService googleMailService;

    /** The microsoft mail service. */
    @Autowired
    private MicrosoftMailService microsoftMailService;

    /**
     * Gets the google nb unread.
     *
     * @param appUser the app user
     * @return the google nb unread
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws Exception   the exception
     */
    @GetMapping(value = "/google/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MailModel> getGoogleNbUnread(@PathVariable final String appUser)
            throws IOException, Exception {

        return new ResponseEntity<MailModel>(googleMailService.getNbUnreadEmails(appUser), HttpStatus.ACCEPTED);
    }

    /**
     * Gets the microsoft nb unread.
     *
     * @param appUser the app user
     * @return the microsoft nb unread
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @GetMapping(value = "/microsoft/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MailModel> getMicrosoftNbUnread(@PathVariable final String appUser) throws IOException {
        return new ResponseEntity<MailModel>(microsoftMailService.getNbUnreadEmails(appUser), HttpStatus.ACCEPTED);
    }

    /**
     * Gets the all nb unread.
     *
     * @param appUser the app user
     * @return the all nb unread
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws Exception   the exception
     */
    @GetMapping(value = "/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MailModel> getAllNbUnread(@PathVariable final String appUser) throws IOException, Exception {
        MailModel googleMail = googleMailService.getNbUnreadEmails(appUser);

        MailModel microsoftMail = microsoftMailService.getNbUnreadEmails(appUser);

        return new ResponseEntity<MailModel>(new MailModel(googleMail.getUnRead() + microsoftMail.getUnRead()),
                HttpStatus.ACCEPTED);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ynov.dap.controller.BaseController#getClassName()
     */
    @Override
    public String getClassName() {
        return MailController.class.getName();
    }
}
