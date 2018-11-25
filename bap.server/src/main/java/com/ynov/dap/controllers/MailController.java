package com.ynov.dap.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.dap.google.business.GoogleMailService;
import com.ynov.dap.google.controllers.BaseController;
import com.ynov.dap.microsoft.business.MicrosoftMailService;
import com.ynov.dap.models.MailModel;

/**
 * The Class MailController.
 */
@RestController
@RequestMapping("email")
public class MailController extends BaseController {

    @Autowired
    private GoogleMailService googleMailService;
    
    @Autowired
    private MicrosoftMailService microsoftMailService;

    @GetMapping(value = "/google/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MailModel> getGoogleNbUnread(@PathVariable final String appUser) {
        try {
            return new ResponseEntity<MailModel>(googleMailService.getNbUnreadEmails(appUser), HttpStatus.ACCEPTED);
        } catch (IOException e) {
            getLogger().error(e.getMessage());
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }
        return new ResponseEntity<MailModel>(new MailModel(0), HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping(value = "/microsoft/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MailModel> getMicrosoftNbUnread(@PathVariable final String appUser) {
        try {
            return new ResponseEntity<MailModel>(microsoftMailService.getNbUnreadEmails(appUser), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }
        return new ResponseEntity<MailModel>(new MailModel(0), HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping(value = "/nbUnread/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MailModel> getAllNbUnread(@PathVariable final String appUser) {
    	MailModel googleMail = new MailModel(0);
		try {
			googleMail = googleMailService.getNbUnreadEmails(appUser);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    	MailModel microsoftMail = microsoftMailService.getNbUnreadEmails(appUser);

        return new ResponseEntity<MailModel>(new MailModel(googleMail.getUnRead() + microsoftMail.getUnRead()), HttpStatus.ACCEPTED);
    }

    /* (non-Javadoc)
     * @see com.ynov.dap.controllers.BaseController#getClassName()
     */
    @Override
    public String getClassName() {
        return MailController.class.getName();
    }
}
