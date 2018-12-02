package fr.ynov.dap.comparateur;

import java.util.Comparator;

import fr.ynov.dap.model.EventAllApi;

/**
 * The Class Sorter.
 */
//TODO bal by Djer |POO| Nom un peut généric. "EventSorter" ? Le nom du package étant "technique" plutot que "metier" il n'aide pas (dans un (sous)package "event" le nom "Soter" aurait pu être suffisant).
public class Sorter implements Comparator<EventAllApi>{

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
    public int compare(final EventAllApi event1, final EventAllApi event2) {
	    //TODO bal by Djer |POO| Il est recommandé de faire des tests de nullité des paramètres dans un comparateur
        return event1.getStart().compareTo(event2.getStart());
    }
	
}
