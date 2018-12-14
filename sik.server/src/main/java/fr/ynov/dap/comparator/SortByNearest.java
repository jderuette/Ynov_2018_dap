package fr.ynov.dap.comparator;

import java.util.Comparator;

import fr.ynov.dap.contract.ApiEvent;

/**
 * Class to sort event form nearest to ...
 * @author Kévin Sibué
 *
 */
public class SortByNearest implements Comparator<ApiEvent> {

    /**
     * Compare two ApiEvent.
     */
    @Override
    //TODO sik by Djer |POO| Pense a tester si ce1 et ce2 sont null (tu peux aussi définir si une date valorisée est ">", "<" ou "=" à une date "null")
    public int compare(final ApiEvent ce1, final ApiEvent ce2) {
        return ce1.getStartDate().compareTo(ce2.getStartDate());
    }

}
