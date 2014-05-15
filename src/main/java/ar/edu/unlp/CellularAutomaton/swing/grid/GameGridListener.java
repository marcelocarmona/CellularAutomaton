package ar.edu.unlp.CellularAutomaton.swing.grid;

import javax.swing.event.ChangeEvent;

/**
 * @author mclo
 *
 */
public interface GameGridListener extends GridListener {

	/**
	 * The neighborhood is changed
	 * @param event
	 */
	void neighborhoodChanged(ChangeEvent event);

}
