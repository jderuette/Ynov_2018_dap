package fr.ynov.dap.comparator;

import java.util.Comparator;

import fr.ynov.dap.model.EventAllApi;

/**
 * The Class Sorter.
 */
public class Sorter implements Comparator<EventAllApi>{

	/**
	 * Compare.
	 *
	 * @param event1 the event 1
	 * @param event2 the event 2
	 * @return the int
	 */
	@Override
    public int compare(final EventAllApi event1, final EventAllApi event2) {
        return event1.getStart().compareTo(event2.getStart());
    }
	
}
