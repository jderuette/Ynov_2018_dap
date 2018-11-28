package fr.ynov.dap.model;

import java.util.ArrayList;
import java.util.Date;

import fr.ynov.dap.data.Guest;
import fr.ynov.dap.model.enumeration.GuestStatusEventEnum;

public interface EventAllApi {

    Date getStart();

    Date getEnd();

    String getSubject();

    GuestStatusEventEnum getCurrentUserStatus();

    GuestStatusEventEnum getStatusForGuest(String userMail);

    ArrayList<Guest> getGuest();
}
