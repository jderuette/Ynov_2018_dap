package fr.ynov.dap.comparator;

import java.util.Comparator;

import fr.ynov.dap.contract.ApiEvent;

/**
 * CLass to sort event form nearest to ...
 * @author Kévin Sibué
 *
 */
public class SortByNearest implements Comparator<ApiEvent> {

    /**
     * Compare two calendarEvent.
     */
    @Override
    public int compare(final ApiEvent ce1, final ApiEvent ce2) {
        return ce1.getStartDate().compareTo(ce2.getStartDate());
    }

}
