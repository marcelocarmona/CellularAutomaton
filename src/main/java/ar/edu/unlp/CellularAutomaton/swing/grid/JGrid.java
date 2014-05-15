package ar.edu.unlp.CellularAutomaton.swing.grid;

import javax.swing.JComponent;
import javax.swing.UIManager;

import ar.edu.unlp.CellularAutomaton.swing.grid.ui.GridUI;

/**
 * @author mclo
 * 
 */
public class JGrid extends JComponent {

	private static final long serialVersionUID = 1L;

	/**
	 * The UI class ID string.
	 */
	private static final String uiClassID = "GridUI";

	/**
	 * Sets the new UI delegate.
	 * 
	 * @param ui
	 *            New UI delegate.
	 */
	public void setUI(GridUI ui) {
		super.setUI(ui);
	}

	/**
	 * Resets the UI property to a value from the current look and feel.
	 * 
	 * @see JComponent#updateUI
	 */
	public void updateUI() {
		if (UIManager.get(getUIClassID()) != null) {
			setUI((GridUI) UIManager.getUI(this));
		} else {
			setUI(new GridUI());
		}
	}

	/**
	 * Returns the UI object which implements the L&F for this component.
	 * 
	 * @return UI object which implements the L&F for this component.
	 * @see #setUI
	 */
	public GridUI getUI() {
		return (GridUI) ui;
	}

	/**
	 * Returns the name of the UI class that implements the L&F for this
	 * component.
	 * 
	 * @return The name of the UI class that implements the L&F for this
	 *         component.
	 * @see JComponent#getUIClassID
	 * @see UIDefaults#getUI
	 */
	public String getUIClassID() {
		return uiClassID;
	}
	
	protected GridModel model;

	
	public GridModel getModel() {
		return model;
	}

	public JGrid(GridModel model) {
		super();
		// Set the model
		this.model = model;
		
		// Set the UI
		this.updateUI();
	}



}
