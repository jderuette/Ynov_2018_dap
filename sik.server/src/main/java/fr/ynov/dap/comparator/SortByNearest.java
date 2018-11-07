package fr.ynov.dap.comparator;

import java.util.Comparator;

import fr.ynov.dap.model.CalendarEvent;

/**
 * CLass to sort event form nearest to ...
 * @author Kévin Sibué
 *
 */
public class SortByNearest implements Comparator<CalendarEvent> {

    /**
     * Compare two calendarEvent.
     */
    @Override
    public int compare(final CalendarEvent ce1, final CalendarEvent ce2) {
        return ce1.getStartDate().compareTo(ce2.getStartDate());
    }

}
