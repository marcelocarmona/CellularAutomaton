package ar.edu.unlp.CellularAutomaton.view;

import java.util.EventListener;

public interface GridPanelListener extends EventListener {
	
	public void sizeChanged(GridPanelEvent gridPanelEvent);

}
