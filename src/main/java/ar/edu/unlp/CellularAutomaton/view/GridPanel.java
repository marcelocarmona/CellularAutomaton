package ar.edu.unlp.CellularAutomaton.view;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.unlp.CellularAutomaton.exception.ShapeException;
import ar.edu.unlp.CellularAutomaton.model.CellState;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeGrid;
import ar.edu.unlp.CellularAutomaton.util.Shape;

/**
 * Draw and render the matrix
 * @author mclo
 */
public class GridPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * cell figures collection for to select the strategy to paint
	 */
	public static CellFigure[] CELL_FIGURES = {new SquareFigure(), new CircleFigure(), new TriangleFigure(), new RhombusFigure(), new HexagonFigure()};

	private GameOfLifeGrid grid;
	private int cellSize;
	private CellState mouseDraggedState;
	private CellFigure cellFigure;
	private List<GridPanelListener>listeners;

	/**
	 * Create the GridPanel.
	 */
	public GridPanel(final int rows, final int cols,final int sizeOfCell) {
		
		grid = new GameOfLifeGrid(cols, rows);
		this.cellSize = sizeOfCell;
		cellFigure = CELL_FIGURES[0];
		
		listeners = new ArrayList<GridPanelListener>();
		
		setBackground(Color.DARK_GRAY);

		//mousePressed
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				int cellX = evt.getX() / cellSize;
				int cellY = evt.getY() / cellSize;
				GameOfLifeCell cell = grid.getCell(cellX, cellY);
				cell.switchState();
				mouseDraggedState = cell.getState();
				repaint(cellX*cellSize,cellY*cellSize,cellSize,cellSize);
			}
		});
		
		//mouseDragged
	    addMouseMotionListener(new MouseMotionAdapter() {
	        public void mouseDragged(MouseEvent evt) {
				int cellX = evt.getX() / cellSize;
				int cellY = evt.getY() / cellSize;
	        	grid.getCell(cellX,cellY).setState(mouseDraggedState);
	        	repaint(cellX*cellSize,cellY*cellSize,cellSize,cellSize);
	        }
	      });
	    
	    //mouseWheelMoved
	    addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int rotation = e.getWheelRotation();
				if(cellSize+rotation >= 5 && cellSize+rotation <= 20){
					cellSize+= rotation;
					resized();
				}
			}
		});

	    //componentResized
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

	/**
	 * @param value cell's size
	 */
	public void setCellSize(int value) {
		cellSize = value;
		resized();
	}
	
	/**
	 * @return number of generation
	 */
	public String getGeneration(){
		return Integer.toString(grid.getGeneration());
	}
	
	/**
	 * @param cellFigure cell figure eg. CircleFigure, SquareFigure, etc..
	 */
	public void setCellFigure(CellFigure cellFigure) {
		this.cellFigure = cellFigure;
		repaint();
	}
	
	/**
	 * next generation of the grid
	 */
	public void nextGeneration() {
		grid.nextGeneration();
		repaint();
		
		//listener event
		GridPanelEvent gridPanelEvent = GridPanelEvent.getGenerationChangedEvent(this, grid.getGeneration());
		for (GridPanelListener listener : listeners) {
			listener.generationChanged(gridPanelEvent);
		}
		
	}

	/**
	 * @param shape loaded in the grid
	 */
	public void loadShape(Shape shape) {		
		try {
			grid.loadShape(shape);
			repaint();
			
			//listener event
			GridPanelEvent gridPanelEvent = GridPanelEvent.getGenerationChangedEvent(this, grid.getGeneration());
			for (GridPanelListener listener : listeners) {
				listener.generationChanged(gridPanelEvent);
			}
			
		} catch (ShapeException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		super.paint(g);

		for (int row = 0; row < grid.getRows(); row++) {
			for (int col = 0; col < grid.getCols(); col++) {	
				g.setColor(new Color(grid.getCell(col, row).getColor()));
				 cellFigure.paint(g,cellSize,col,row);
			}

		}
	}

	/**
	 * resize the grid
	 */
	private void resized() {
		int cols = getWidth() / cellSize;
		int rows = getHeight() / cellSize;
		if (cols != grid.getCols() || rows != grid.getRows()){
			grid.resize(cols, rows);
			repaint();
			
			//listener event
			GridPanelEvent gridPanelEvent = GridPanelEvent.getSizeChangedEvent(this, grid.getCols(), grid.getRows(), cellSize);
			for (GridPanelListener listener : listeners) {
				listener.sizeChanged(gridPanelEvent);
			}
		}
	}

}
