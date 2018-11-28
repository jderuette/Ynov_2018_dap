package fr.ynov.dap.comparator;

import java.util.Comparator;

import fr.ynov.dap.model.EventAllApi;

public class Sorter implements Comparator<EventAllApi>{

	@Override
    public int compare(final EventAllApi event1, final EventAllApi event2) {
        return event1.getStart().compareTo(event2.getStart());
    }
	
}
