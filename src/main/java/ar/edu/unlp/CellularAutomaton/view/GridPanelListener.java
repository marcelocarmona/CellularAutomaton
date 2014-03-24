package ar.edu.unlp.CellularAutomaton.view;

import java.util.EventListener;

/**
 * Listener interface for GridPanel
 * @see GridPanel
 * @author mclo
 */
public interface GridPanelListener extends EventListener {
	
	/**
	 * The grid size is changed
	 * @param gridPanelEvent event object
	 */
	public void sizeChanged(GridPanelEvent gridPanelEvent);
}
