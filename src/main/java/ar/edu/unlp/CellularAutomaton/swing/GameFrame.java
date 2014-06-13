package ar.edu.unlp.CellularAutomaton.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;

import ar.edu.unlp.CellularAutomaton.exception.ShapeException;
import ar.edu.unlp.CellularAutomaton.model.Alive;
import ar.edu.unlp.CellularAutomaton.model.CellState;
import ar.edu.unlp.CellularAutomaton.model.Dead;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeGrid;
import ar.edu.unlp.CellularAutomaton.model.Neighborhood;
import ar.edu.unlp.CellularAutomaton.model.Rule;
import ar.edu.unlp.CellularAutomaton.model.StateRuleImpl;
import ar.edu.unlp.CellularAutomaton.swing.checkList.RuleCheckListModel;
import ar.edu.unlp.CellularAutomaton.swing.grid.GameGridModel;
import ar.edu.unlp.CellularAutomaton.swing.grid.GridListener;
import ar.edu.unlp.CellularAutomaton.swing.grid.JGrid;
import ar.edu.unlp.CellularAutomaton.util.CellSize;
import ar.edu.unlp.CellularAutomaton.util.GameThread;
import ar.edu.unlp.CellularAutomaton.util.Shape;
import ar.edu.unlp.CellularAutomaton.util.SpeedTime;
import ar.edu.unlp.CellularAutomaton.util.CellFigures;

import java.awt.Toolkit;

public class GameFrame extends JFrame {

	/**
	 * Main window contains a gridComponent
	 * 
	 * @author mclo
	 */

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private AboutDialog aboutDialog;
	private JFrame frameRule;
	private GameThread gameThread;

	private JGrid gridComponent;
	private JPanel bottomPanel;

	private JComboBox<Shape> shapeBox;
	private JComboBox<SpeedTime> speedBox;
	private JComboBox<CellSize> sizeBox;
	private JComboBox<CellState> cellColorBox;
	private JComboBox<CellFigures> cellFigureBox;

	private JToggleButton tglbtnStart;
	private JButton btnNext;
	private JButton btnRule;

	private JLabel lblGrid;
	private JLabel lblCellSize;
	private JLabel lblDelay;
	private JLabel lblGenerations;

	/**
	 * Create the frame.
	 */
	public GameFrame() {
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						GameFrame.class
								.getResource("/ar/edu/unlp/CellularAutomaton/util/icon.png")));
		setTitle("Cellular Automaton");
		setBounds(100, 100, 699, 344);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//initialize decorator state rule
		GameOfLifeCell.ALIVE.setStateRule(new RuleCheckListModel(GameOfLifeCell.ALIVE.getStateRule()));
		GameOfLifeCell.DEAD.setStateRule(new RuleCheckListModel(GameOfLifeCell.DEAD.getStateRule()));

		// gridComponent
		GameGridModel gameGridModel = new GameGridModel(
				new GameOfLifeGrid(5, 5, new Rule(	"Life", 
													"Conway's Game of Life", 
													Neighborhood.MOORE,
													GameOfLifeCell.ALIVE, 
													GameOfLifeCell.DEAD)));
		gridComponent = new JGrid(gameGridModel);
		gridComponent.getModel().addGridListener(new GridListener() {

			public void stateChanged(ChangeEvent e) {
				GameGridModel gameGridModel = (GameGridModel) e.getSource();
				lblGrid.setText("Grid(" + gameGridModel.getCols() + ", "
						+ gameGridModel.getRows() + ")");
				lblGenerations.setText("Generations: "
						+ Integer.toString(gameGridModel.getGeneration()));

			}

			@Override
			public void cellSizeChanged(ChangeEvent event) {
				GameGridModel gameGridModel = (GameGridModel) event.getSource();
				lblCellSize.setText("CellSize: " + gameGridModel.getCellSize()
						+ "px");
			}
		});
		contentPane.add(gridComponent, BorderLayout.CENTER);

		// frameRule
		frameRule = new RuleFrame(gameGridModel);

		// aboutDialog
		aboutDialog = new AboutDialog();

		// bottomPanel
		bottomPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) bottomPanel.getLayout();
		flowLayout.setHgap(20);
		bottomPanel.setBackground(new Color(255, 250, 205));
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		lblGrid = new JLabel();
		lblGrid.setText("Grid()");
		bottomPanel.add(lblGrid);

		lblCellSize = new JLabel("CellSize");
		bottomPanel.add(lblCellSize);

		lblDelay = new JLabel("Delay");
		bottomPanel.add(lblDelay);

		lblGenerations = new JLabel("Generations: 0");
		bottomPanel.add(lblGenerations);

		// topPanel
		JPanel topPanel = new JPanel();
		contentPane.add(topPanel, BorderLayout.NORTH);

		shapeBox = new JComboBox<Shape>();
		shapeBox.setMaximumRowCount(9);
		shapeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Shape shape = (Shape) shapeBox.getSelectedItem();
				try {
					((GameGridModel) gridComponent.getModel()).loadShape(shape);
				} catch (ShapeException e) {
					showShapeException(e);
				}
			}
		});
		shapeBox.setModel(new DefaultComboBoxModel<Shape>(Shape.values()));
		topPanel.add(shapeBox);

		tglbtnStart = new JToggleButton("Start");
		tglbtnStart.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					tglbtnStart.setText("Stop");
					startManagerOfThreads();
					btnNext.setEnabled(false);
				} else {
					tglbtnStart.setText("Start");
					stopManagerOfThreads();
					newManagerOfThreads();
					btnNext.setEnabled(true);
				}
			}
		});
		topPanel.add(tglbtnStart);

		btnNext = new JButton("Next");
		// btnNext.setIcon(new
		// ImageIcon(Toolkit.getDefaultToolkit().getImage((GameFrame.class.getResource("/ar/edu/unlp/CellularAutomaton/util/ic_action_play.png")))));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((GameGridModel) gridComponent.getModel()).nextGeneration();
			}
		});
		topPanel.add(btnNext);

		
		btnRule = new JButton("Rule");
		btnRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameRule.setVisible(true);
			}
		});
		topPanel.add(btnRule);

		sizeBox = new JComboBox<CellSize>();
		sizeBox.setModel(new DefaultComboBoxModel<CellSize>(CellSize.values()));
		sizeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CellSize CellSize = (CellSize) sizeBox.getSelectedItem();
				((GameGridModel) gridComponent.getModel()).setCellSize(CellSize
						.getValue());
			}
		});
		topPanel.add(sizeBox);

		speedBox = new JComboBox<SpeedTime>();
		speedBox.setModel(new DefaultComboBoxModel<SpeedTime>(SpeedTime
				.values()));
		speedBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameThread.setSleepTime(getSpeedTime());
			}
		});
		topPanel.add(speedBox);

		cellColorBox = new JComboBox<CellState>();
		cellColorBox.setModel(new DefaultComboBoxModel<CellState>(
				GameOfLifeCell.STATES));
		cellColorBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CellState cellSate = (CellState) cellColorBox.getSelectedItem();
				Color color = JColorChooser.showDialog(contentPane,
						"Choose a color", new Color(cellSate.getColor()));
				if (color != null) {
					cellSate.setColor(color.getRGB());
					gridComponent.repaint();
				}
			}
		});
		topPanel.add(cellColorBox);

		cellFigureBox = new JComboBox<CellFigures>();
		cellFigureBox.setModel(new DefaultComboBoxModel<CellFigures>(
				CellFigures.values()));

		cellFigureBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CellFigures figure = (CellFigures) cellFigureBox
						.getSelectedItem();
				figure.getId();

				gridComponent.getModel().setCellFigure(figure.getId());
			}
		});
		topPanel.add(cellFigureBox);

		// MenuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnHelp = new JMenu("Help");
		mnHelp.setMnemonic(KeyEvent.VK_H);
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAboutDialog();
			}
		});
		mnHelp.add(mntmAbout);

		// init thread
		newManagerOfThreads();
	}

	/**
	 * Create a Thread
	 */
	private void newManagerOfThreads() {
		gameThread = new GameThread(((GameGridModel) gridComponent.getModel()),
				getSpeedTime());
	}

	/**
	 * Start Thread
	 */
	private void startManagerOfThreads() {
		gameThread.start();
	}

	/**
	 * Stop Thread
	 */
	private void stopManagerOfThreads() {
		gameThread.finish();
	}

	/**
	 * Return the speed time selected in the comboBox
	 * 
	 * @return speed time
	 */
	private int getSpeedTime() {
		SpeedTime speedTime = (SpeedTime) speedBox.getSelectedItem();
		lblDelay.setText("Delay: " + speedTime.getValue() * 0.001 + "s");
		return speedTime.getValue();
	}

	/**
	 * Popup About Dialog
	 */
	private void showAboutDialog() {
		aboutDialog.setVisible(true);
	}

	private void showShapeException(ShapeException e) {
		JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
				JOptionPane.ERROR_MESSAGE);
	}

}
