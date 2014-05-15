package ar.edu.unlp.CellularAutomaton.swing.grid.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.ComponentUI;

import ar.edu.unlp.CellularAutomaton.swing.grid.GridListener;
import ar.edu.unlp.CellularAutomaton.swing.grid.JGrid;


public class GridUI extends ComponentUI {
	
	//JComponent
	protected JGrid grid;
	
	//Listeners
	protected MouseListener mouseListener;

	protected MouseMotionListener mouseMotionListener;
	
	protected MouseWheelListener mouseWheelListener;
	
	protected ComponentListener componentListener;

	protected GridListener gameOfLifeGridListener;
	
	
	/**
	 * cell figures collection for to select the strategy to paint
	 */
	public static CellFigure[] CELL_FIGURES = {new SquareFigure(), new CircleFigure(), new TriangleFigure(), new RhombusFigure(), new HexagonFigure()};
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
	 */
	public static ComponentUI createUI(JComponent c) {
		return new GridUI();
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#installUI(javax.swing.JComponent)
	 */
	public void installUI(JComponent c) {
		this.grid = (JGrid) c;c.setBackground(Color.BLUE);
//		installDefaults();
//		installComponents();
		installListeners();


		c.setBorder(new EmptyBorder(1, 1, 1, 1));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
	 */
	public void uninstallUI(JComponent c) {
		uninstallListeners();
//		uninstallComponents();
//		uninstallDefaults();

		this.grid = null;
	}
	

	public void installListeners() {
		
		//mousePressed
		this.mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				int cellSize = grid.getModel().getCellSize();
				int cellX = evt.getX() / cellSize;
				int cellY = evt.getY() / cellSize;
				grid.getModel().mouseClickAction(cellX ,cellY);
			}
		};
		
		//mouseDragged
	    mouseMotionListener = new MouseMotionAdapter() {
	        public void mouseDragged(MouseEvent evt) {
	        	int cellSize = grid.getModel().getCellSize();
				int cellX = evt.getX() / cellSize;
				int cellY = evt.getY() / cellSize;
				grid.getModel().mouseDraggedAction(cellX ,cellY);
	        }
	      };
	    

	    //mouseWheelMoved
	    mouseWheelListener = new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int rotation = e.getWheelRotation();
				grid.getModel().mouseWheelAction(rotation);
//				resized();
			}
		};
		
		// componentResized
		componentListener = new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				resized();
			}
		};
		
		// GridListener
		gameOfLifeGridListener = new GridListener() {
			public void stateChanged(ChangeEvent e) {
				grid.repaint();
			}

			public void cellSizeChanged(ChangeEvent event) {
				resized();
			}
		};
		
		//Add Listeners
		this.grid.addMouseListener(this.mouseListener);
		this.grid.addMouseMotionListener(this.mouseMotionListener);
		this.grid.addMouseWheelListener(this.mouseWheelListener);
		this.grid.addComponentListener(this.componentListener);
		this.grid.getModel().addGridListener(this.gameOfLifeGridListener);
		
	}
	
	private void resized(){
		int cellSize = grid.getModel().getCellSize();
		int cols = grid.getWidth() / cellSize;
		int rows = grid.getHeight() / cellSize;
		grid.getModel().componentResizedAction(cols, rows);
	}
	
	public void uninstallListeners() {
		this.grid.removeMouseListener(this.mouseListener);
		this.mouseListener = null;
		
		this.grid.removeMouseMotionListener(this.mouseMotionListener);
		this.mouseMotionListener = null;
		
		this.grid.removeMouseWheelListener(this.mouseWheelListener);
		this.mouseWheelListener = null;
		
		this.grid.removeComponentListener(this.componentListener);
		this.componentListener = null;
		
		this.grid.getModel().removeGridListener(this.gameOfLifeGridListener);
		this.gameOfLifeGridListener = null;
	}
	

	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		int rows = this.grid.getModel().getRows();
		int cols = this.grid.getModel().getCols();
		int cellSize = this.grid.getModel().getCellSize();
		CellFigure cellFigure = CELL_FIGURES[this.grid.getModel().getCellFigure()];
		
		//draw Background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
		
		//draw cells
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				g.setColor(this.grid.getModel().getCellColor(col,row));
				cellFigure.paint(g, cellSize, col, row);

			}
		}
	}

	    
	    

}
