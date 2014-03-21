package ar.edu.unlp.CellularAutomaton.view;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.unlp.CellularAutomaton.exception.ShapeException;
import ar.edu.unlp.CellularAutomaton.model.CellState;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeGrid;
import ar.edu.unlp.CellularAutomaton.util.Shape;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GameOfLifeGrid grid;
	private int cellSize = 20;
	private CellState saveState;
	private List<GridPanelListener>listeners;

	/**
	 * Create the GridPanel.
	 */
	public GridPanel(final int rows, final int cols) {
		
		grid = new GameOfLifeGrid(cols, rows);

		
		listeners = new ArrayList<GridPanelListener>();
		
		setBackground(Color.LIGHT_GRAY);
		
		addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent evt) {
				int cellX = evt.getX() / cellSize;
				int cellY = evt.getY() / cellSize;
				GameOfLifeCell cell = grid.getCell(cellX, cellY);
				cell.switchState();
				saveState = cell.getState();
				repaint(cellX*cellSize,cellY*cellSize,cellX*cellSize+cellSize,cellY*cellSize+cellSize);
			}

		});
		
	    addMouseMotionListener(new MouseMotionAdapter() {
	        public void mouseDragged(MouseEvent evt) {
				int cellX = evt.getX() / cellSize;
				int cellY = evt.getY() / cellSize;
	        	grid.getCell(cellX,cellY).setState(saveState);
	        	repaint(cellX*cellSize,cellY*cellSize,cellX*cellSize+cellSize,cellY*cellSize+cellSize);
	        }
	      });

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resized();
			}
		});


	}
	
	/**
	 * Add listener for this Panel
	 * @param listener listener object
	 */
	public void addGridPanelListener(GridPanelListener listener){
		listeners.add(listener);
	}

	public void nextGeneration() {
		grid.nextGeneration();
	}
	
	public String getGeneration(){
		return Integer.toString(grid.getGeneration());
	}

	public void loadShape(Shape shape) {		
		try {
			grid.loadShape(shape);
		} catch (ShapeException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for (int row = 0; row < grid.getRows(); row++) {
			for (int col = 0; col < grid.getCols(); col++) {
				
				g.setColor(new Color(grid.getCell(col, row).getColor()));
//				g.fillOval(col * cellSize, row * cellSize, cellSize, cellSize);
				g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
				g.setColor(Color.BLACK);
				g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
//				g.drawOval(col * cellSize, row * cellSize, cellSize, cellSize);
			}
//			g.setColor(Color.ORANGE);
//			g.drawString(grid.toString(),10,20);

		}
	}

	public void resized() {
		int cols = getWidth() / cellSize;
		int rows = getHeight() / cellSize;
		if (cols != grid.getCols() || rows != grid.getRows()){
			grid.resize(cols, rows);

			repaint();
			
			GridPanelEvent gridPanelEvent = GridPanelEvent.getSizeChangedEvent(this, grid.getCols(), grid.getRows());
			for (GridPanelListener listener : listeners) {
				listener.sizeChanged(gridPanelEvent);
			}
		}
	}

	public void setCellSize(int value) {
		cellSize = value;
		resized();
	}

	public int getCols() {
		return grid.getCols();
	}

	public int getRows() {
		return grid.getRows();
	}

	/**
	 * creado para probar el thead concurrente
	 */
	public GameOfLifeGrid getGrid() {
		return grid;
	}

}
