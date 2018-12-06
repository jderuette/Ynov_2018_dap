package fr.ynov.dap.dap.comparator;

import java.util.Comparator;

import fr.ynov.dap.dap.model.GoogleCalendarResponse;

/**
 * The Class SortByDate.
 */
//TODO bot by Djer |JavaDoc| Précise que ca par "start Date" ? (soit dans la JavaDoc soit dans le nom de la classe, soit les deux)
public class SortByDate implements Comparator<GoogleCalendarResponse>{

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
    public int compare(final GoogleCalendarResponse event1, final GoogleCalendarResponse event2) {
        return event1.getStart().compareTo(event2.getStart());
    }
	
}
