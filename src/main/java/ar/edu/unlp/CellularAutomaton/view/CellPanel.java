package ar.edu.unlp.CellularAutomaton.view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell;

public class CellPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameOfLifeCell cell;

	/**
	 * Create the CellPanel.
	 */
	public CellPanel(final GameOfLifeCell cell) {
		this.cell = cell;
		setBorder(BorderFactory.createLoweredBevelBorder());
		updateBackground();

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				cell.switchState();
				updateBackground();
				System.out.println(cell.getAliveNeighbors());
			}
		});
	}

	/**
	 * REFACTORING
	 */
	public void updateBackground() {
		if (cell.getState().isAlive())
			setBackground(Color.WHITE);
		else
			setBackground(Color.DARK_GRAY);
	}

}
