package fr.ynov.dap.comparateur;

import java.util.Comparator;

import fr.ynov.dap.model.EventAllApi;

/**
 * The Class Sorter.
 */
public class Sorter implements Comparator<EventAllApi>{

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
    public int compare(final EventAllApi event1, final EventAllApi event2) {
        return event1.getStart().compareTo(event2.getStart());
    }
	
}
