package fr.ynov.dap.contract;

import java.util.Date;

import fr.ynov.dap.model.enumeration.AttendeeEventStatusEnum;

/**
 * This interface allow to abstract Event from different kind of API (e.g. Microsoft, Google, ...).
 * @author Kévin Sibué
 *
 */
public interface ApiEvent {

    /**
     * Current event starting date.
     * @return Starting date
     */
    Date getStartDate();

    /**
     * Current event ending date.
     * @return Ending date
     */
    Date getEndDate();

    /**
     * Current event subject.
     * @return Subject
     */
    String getSubject();

    /**
     * Current user status.
     * @return User status
     */
    AttendeeEventStatusEnum getCurrentUserStatus();

    /**
     * Get status for a specific mail.
     * @param userMail User mail
     * @return Status
     */
    AttendeeEventStatusEnum getStatusForAttendee(String userMail);

}
