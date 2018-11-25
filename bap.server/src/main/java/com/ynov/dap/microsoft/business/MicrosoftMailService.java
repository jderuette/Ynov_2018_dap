package com.ynov.dap.microsoft.business;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynov.dap.data.AppUser;
import com.ynov.dap.microsoft.data.MicrosoftAccount;
import com.ynov.dap.microsoft.models.Folder;
import com.ynov.dap.microsoft.models.TokenResponse;
import com.ynov.dap.models.MailModel;
import com.ynov.dap.repositories.AppUserRepository;

@Service
public class MicrosoftMailService {
	
    @Autowired
    private AppUserRepository appUserRepository;
    
    private Integer getNbUnreadEmails(final MicrosoftAccount microsoftAccount) {

        if (microsoftAccount == null) {
            return 0;
        }

        String email = microsoftAccount.getEmail();
        String tenantId = microsoftAccount.getTenantId();
        TokenResponse tokens = microsoftAccount.getTokenResponse();

        System.out.println(email);
        System.out.println(tenantId);
        System.out.println(tokens);

        /*
        if (StrUtils.isNullOrEmpty(tenantId) || StrUtils.isNullOrEmpty(email) || tokens == null) {
            return 0;
        }
        */

        //TokenResponse newTokens = getToken(microsoftAccount);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        Folder inboxFolder = null;
        try {
			inboxFolder = outlookService.getFolder("inbox").execute().body();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        return inboxFolder.getUnreadItemCount();
    }

	public MailModel getNbUnreadEmails(String gUser) {
		
	    AppUser appUser = appUserRepository.findByName(gUser);

        MailModel mail = new MailModel();
	    Integer nbUnreadMails = 0;

	    for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {

	    	nbUnreadMails += getNbUnreadEmails(account);

	    }
        mail.setUnRead(nbUnreadMails);

        //getLogger().info("nb messages unread " + mail.getUnRead() + " for user : " + user);
        return mail;
	}
	
	/*
    @Override
    public String getClassName() {
        return GoogleMailService.class.getName();
    }
	*/
	
	
}
