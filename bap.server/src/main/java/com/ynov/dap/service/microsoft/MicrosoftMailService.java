package com.ynov.dap.service.microsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.microsoft.MicrosoftAccount;
import com.ynov.dap.model.MailModel;
import com.ynov.dap.model.microsoft.Folder;
import com.ynov.dap.model.microsoft.Message;
import com.ynov.dap.model.microsoft.PagedResult;
import com.ynov.dap.model.microsoft.TokenResponse;
import com.ynov.dap.repository.AppUserRepository;
import com.ynov.dap.service.BaseService;

/**
 * The Class MicrosoftMailService.
 */
@Service
public class MicrosoftMailService extends BaseService {

    /** The Constant MAX_RESULT_EVENTS. */
    private static final Integer MAX_RESULT_EVENTS = 10;

    /** The app user repository. */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Gets the nb unread emails.
     *
     * @param account the account
     * @return the nb unread emails
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private Integer getNbUnreadEmails(final MicrosoftAccount account) throws IOException {
        if (account == null) {
            return 0;
        }

        String email = account.getEmail();
        String tenantId = account.getTenantId();
        TokenResponse tokens = account.getTokenResponse();
        tokens = AuthHelper.ensureTokens(tokens, tenantId);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        Folder inboxFolder = outlookService.getFolder("inbox").execute().body();

        if (inboxFolder == null) {
            return 0;
        }

        getLogger().info("Nb messages unread " + inboxFolder.getUnreadItemCount() + " for the account '"
                + account.getName() + "'");
        return inboxFolder.getUnreadItemCount();
    }

    /**
     * Gets the nb unread emails.
     *
     * @param userKey the user key
     * @return the nb unread emails
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public MailModel getNbUnreadEmails(final String userKey) throws IOException {
        AppUser appUser = appUserRepository.findByName(userKey);

        if (appUser == null) {
            getLogger().error("userKey '" + userKey + "' not found");
            return new MailModel(0);
        }

        MailModel mail = new MailModel();
        Integer nbUnreadMails = 0;

        for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {

            nbUnreadMails += getNbUnreadEmails(account);

        }
        mail.setUnRead(nbUnreadMails);

        getLogger().info("Total nb messages unread " + mail.getUnRead() + " for userKey : " + userKey);
        return mail;
    }

    /**
     * Gets the emails.
     *
     * @param account the account
     * @return the emails
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private Message[] getEmails(final MicrosoftAccount account) throws IOException {
        String email = account.getEmail();
        String tenantId = account.getTenantId();
        TokenResponse tokens = account.getTokenResponse();
        tokens = AuthHelper.ensureTokens(tokens, tenantId);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        String folder = "inbox";
        String sort = "receivedDateTime DESC";
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        Integer maxResults = MAX_RESULT_EVENTS;

        PagedResult<Message> messages = null;
        messages = outlookService.getMessages(folder, sort, properties, maxResults).execute().body();

        return messages.getValue();
    }

    /**
     * Gets the emails.
     *
     * @param userKey the user key
     * @return the emails
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public List<Message[]> getEmails(final String userKey) throws IOException {
        List<Message[]> messages = new ArrayList<Message[]>();

        AppUser appUser = appUserRepository.findByName(userKey);
        if (appUser == null) {
            getLogger().error("userKey '" + userKey + "' not found");
            return messages;
        }

        for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {
            messages.add(getEmails(account));
        }

        return messages;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ynov.dap.service.BaseService#getClassName()
     */
    @Override
    public String getClassName() {
        return MicrosoftMailService.class.getName();
    }
}
