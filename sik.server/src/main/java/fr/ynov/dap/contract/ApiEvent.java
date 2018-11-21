package fr.ynov.dap.contract;

import java.util.Date;

import fr.ynov.dap.model.enumeration.AttendeeEventStatusEnum;

public interface ApiEvent {

    Date getStartDate();

    Date getEndDate();

    String getSubject();

    AttendeeEventStatusEnum getCurrentUserStatus();

    AttendeeEventStatusEnum getStatusForAttendee(final String userMail);

}
