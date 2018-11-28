package fr.ynov.dap.dap;

import com.google.api.services.calendar.model.Event;
import java.util.Comparator;
import java.util.Date;

/**
 * The Class CompareEvent.
 */
public class CompareEvent implements Comparator<Event> {
	@Override
	public int compare(final Event e1, final Event e2) {
		Date e1Date = new Date(e1.getStart().getDateTime().getValue());
		Date e2Date = new Date(e2.getStart().getDateTime().getValue());
		return e1Date.compareTo(e2Date);
	}
}
