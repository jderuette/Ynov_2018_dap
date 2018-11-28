package fr.ynov.dap.model;

import java.util.ArrayList;
import java.util.Date;

import fr.ynov.dap.data.Guest;
import fr.ynov.dap.model.enumeration.GuestStatusEventEnum;

public interface EventAllApi {
	 /**
     * Current event starting date.
     * @return Starting date
     */
    Date getStart();

    /**
     * Current event ending date.
     * @return Ending date
     */
    Date getEnd();

    /**
     * Current event subject.
     * @return Subject
     */
    String getSubject();

    /**
     * Current user status.
     * @return User status
     */
    GuestStatusEventEnum getCurrentUserStatus();

    /**
     * Get status for a specific mail.
     * @param userMail User mail
     * @return Status
     */
    GuestStatusEventEnum getStatusForGuest(String userMail);

    ArrayList<Guest> getGuest();
}
