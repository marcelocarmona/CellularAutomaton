package ar.edu.unlp.CellularAutomaton.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import ar.edu.unlp.CellularAutomaton.model.GameOfLifeGrid.ManagerOfThreads;
import ar.edu.unlp.CellularAutomaton.util.Shape;
import ar.edu.unlp.CellularAutomaton.util.SpeedTime;


import ar.edu.unlp.CellularAutomaton.util.CellSize;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;


public class Window extends JFrame {


	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private GridPanel gridPanel;
	private JLabel lblNumGenerations;
	private JButton btnNext;

	private JComboBox<Shape> shapeBox;
	private JToggleButton tglbtnStart;
	
	private JComboBox<SpeedTime> speedBox;
	private JLabel lblCells;
	private JComboBox<CellSize> sizeBox;
	private JLabel lblThreads;
	private JComboBox<Integer> threadBox;
	private JPanel bottomPanel;
	

	private ManagerOfThreads managerOfThreads;

	/**
	 * Create the frame.
	 */
	public Window() {
		setTitle("Game Of Life");
		setBounds(100, 100, 678, 352);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					nextGeneration();
					System.out.println("ENTER pressed");
				}
			}
		});
		
		
		//gridPanel <----------------------------------REFACTORING..........................
		int cellsize = CellSize.values()[0].getValue();
		gridPanel = new GridPanel(20,20);
		gridPanel.addGridPanelListener(new GridPanelListener() {
			
			@Override
			public void sizeChanged(GridPanelEvent gridPanelEvent) {
				
				lblCells.setText("Grid("+gridPanelEvent.getCols()+", "+gridPanelEvent.getRows()+")");
				
				int threads = gridPanelEvent.getRows();
				Integer[] arrayThreads = new Integer[threads];
				for (int i = 0; i < threads; i++) {
					arrayThreads[i]=i+1;
				}
				threadBox.setModel(new DefaultComboBoxModel<Integer>(arrayThreads));
				
			}
		});
		contentPane.add(gridPanel, BorderLayout.CENTER);	

		//bottomPanel
		bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(255, 250, 205));
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		
		lblCells = new JLabel("Grid("+gridPanel.getCols()+", "+gridPanel.getRows()+")");
		bottomPanel.add(lblCells);
		
		JLabel lblGenerations = new JLabel("Generations:");
		bottomPanel.add(lblGenerations);
		
		lblNumGenerations = new JLabel("0");
		bottomPanel.add(lblNumGenerations);

		
		//topPanel
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		contentPane.add(topPanel, BorderLayout.NORTH);
		
		shapeBox = new JComboBox<Shape>();
		shapeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Shape shape = (Shape) shapeBox.getSelectedItem();
				gridPanel.loadShape(shape);
				showGeneration();
				repaint();
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
					shapeBox.setEnabled(false);
					threadBox.setEnabled(false);
				} else {
					tglbtnStart.setText("Start");
					stopManagerOfThreads();
					newManagerOfThreads();
					btnNext.setEnabled(true);
					shapeBox.setEnabled(true);
					threadBox.setEnabled(true);
				}
			}
		});
		topPanel.add(tglbtnStart);
		
		
		final JToggleButton tglbtnPause = new JToggleButton("Pause");
		tglbtnPause.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					tglbtnPause.setText("Resume");
					managerOfThreads.pause();
				} else {
					tglbtnPause.setText("Pause");
					managerOfThreads.resume();
				}
			}
		});
		topPanel.add(tglbtnPause);
		
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextGeneration();
			}
		});
		
		speedBox = new JComboBox<SpeedTime>();
		speedBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				managerOfThreads.setSleepTime(getSpeedTime());
			}
		});
		speedBox.setModel(new DefaultComboBoxModel<SpeedTime>(SpeedTime.values()));
		topPanel.add(speedBox);
		topPanel.add(btnNext);
		
		sizeBox = new JComboBox<CellSize>();
		sizeBox.setModel(new DefaultComboBoxModel<CellSize>(CellSize.values()));
		sizeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CellSize CellSize = (CellSize) sizeBox.getSelectedItem();
				gridPanel.setCellSize(CellSize.getValue());
			}
		});
		topPanel.add(sizeBox);
		
		lblThreads = new JLabel("Threads: ");
		topPanel.add(lblThreads);
		
		threadBox = new JComboBox<Integer>();
		threadBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				managerOfThreads.setNumOfThreads(getNumOfThreads());
			}
		});
		threadBox.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1}));
		topPanel.add(threadBox);
		
		//MenuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAboutDialog();
			}
		});
		mnHelp.add(mntmAbout);
		
		//init thread
		newManagerOfThreads();
	}
	
	
	/**
	 * Go to the next generation.
	 */
	public void nextGeneration() {
		gridPanel.nextGeneration();
		showGeneration();
		repaint();
	}

	/**
	 * Create a Thread
	 */
	private void newManagerOfThreads(){
//		startThread = new StartThread(this,getSpeedTime());
		managerOfThreads = gridPanel.newManagerOfThreads(getSpeedTime(), getNumOfThreads());
		managerOfThreads.addObserver(new Observer() {
			
			@Override
			public void update(Observable o, Object arg) {
				System.out.println("PROBANDOOOO");
				gridPanel.repaint();
				showGeneration();
				
			}
		});
	}
	
	/**
	 * Start Thread
	 */
	private void startManagerOfThreads(){
//		startThread.start();
//		singleThread.start();
//		singleThread2.start();
		managerOfThreads.start();
	}
	/**
	 * Stop Thread
	 */
	private void stopManagerOfThreads(){
//		startThread.finish();
//		done =true;
//		singleThread.finish();
//		singleThread2.finish();
		managerOfThreads.stop();
		
	}
	
	/**
	 * Return the speed time selected in the comboBox
	 * 
	 * @return speed time
	 */
	private int getSpeedTime(){
		SpeedTime speedTime = (SpeedTime) speedBox.getSelectedItem();
		return speedTime.getValue();
	}
	
	/**
	 * Return the number of threads selected in the comboBox
	 * 
	 * @return number of thread
	 */
	private int getNumOfThreads(){
		int numOfThreads = (int) threadBox.getSelectedItem();
		return numOfThreads;
	}
	
	/**
	 * update generation label
	 */
	private void showGeneration(){
		lblNumGenerations.setText(gridPanel.getGeneration());
	}
	
	/**
	 * Popup About Dialog
	 */
	private void showAboutDialog(){
		JOptionPane.showMessageDialog(this, "Game Of Life - Carmona Marcelo - 2014");
	}
	


}
