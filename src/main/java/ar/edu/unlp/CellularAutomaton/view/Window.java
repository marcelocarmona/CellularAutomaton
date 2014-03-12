package ar.edu.unlp.CellularAutomaton.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

import ar.edu.unlp.CellularAutomaton.util.Shape;
import ar.edu.unlp.CellularAutomaton.util.SingleThread;
import ar.edu.unlp.CellularAutomaton.util.SingleThread2;
import ar.edu.unlp.CellularAutomaton.util.SpeedTime;
import ar.edu.unlp.CellularAutomaton.util.StartThread;

import javax.swing.SwingConstants;

import java.awt.FlowLayout;

import ar.edu.unlp.CellularAutomaton.util.CellSize;

import java.awt.Color;
import java.util.concurrent.CyclicBarrier;

import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

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
	private JLabel lblTheads;
	private JComboBox threadBox;
	private JPanel bottomPanel;
	
	private StartThread startThread;
	private SingleThread singleThread;
	private SingleThread2 singleThread2;
	public boolean done =false;

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
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resized();
			}
		});
		
		//gridPanel <----------------------------------REFACTORING..........................
		int cellsize = CellSize.values()[0].getValue();
		gridPanel = new GridPanel(20,20);
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
					startStartThread();
					btnNext.setEnabled(false);
					shapeBox.setEnabled(false);
				} else {
					tglbtnStart.setText("Start");
					stopStartThread();
					newStartThread();
					btnNext.setEnabled(true);
					shapeBox.setEnabled(true);
				}
			}
		});
		topPanel.add(tglbtnStart);
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextGeneration();
			}
		});
		
		speedBox = new JComboBox<SpeedTime>();
		speedBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startThread.setSleepTime(getSpeedTime());
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
//				gridPanel.resize(CellSize.getWidth(),CellSize.getHeight());
//				repaint();
			}
		});
		topPanel.add(sizeBox);
		
		lblTheads = new JLabel("Theads: ");
		topPanel.add(lblTheads);
		
		threadBox = new JComboBox();
		threadBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
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
		newStartThread();
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
	private void newStartThread(){
		startThread = new StartThread(this,getSpeedTime());
//		final CyclicBarrier countAliveNeighborsBarrier = new CyclicBarrier(2);
//		final CyclicBarrier transitionFunctionBarrier = new CyclicBarrier(2,new Runnable(){
//			
//			int i= 0;
//			public void run() {repaint();i++;
//				try {Thread.sleep(getSpeedTime());} catch (InterruptedException e) {e.printStackTrace();}
//				showGeneration();
//				lblNumGenerations.setText(Integer.toString(i));
//				
//			}
//			
//		});
//
//		singleThread = new SingleThread(this,gridPanel.getGrid(), getSpeedTime(), countAliveNeighborsBarrier, transitionFunctionBarrier);
//		singleThread2 = new SingleThread2(this,gridPanel.getGrid(), getSpeedTime(), countAliveNeighborsBarrier, transitionFunctionBarrier);
	}
	
	/**
	 * Start Thread
	 */
	private void startStartThread(){
		startThread.start();
//		singleThread.start();
//		singleThread2.start();
	}
	/**
	 * Stop Thread
	 */
	private void stopStartThread(){
		startThread.finish();
////		done =true;
//		singleThread.finish();
//		singleThread2.finish();
		
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
	
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		super.update(g);
		lblCells.setText("Grid("+gridPanel.getCols()+", "+gridPanel.getRows()+")");
	}


	@Override
	public void paintComponents(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
		lblCells.setText("Grid("+gridPanel.getCols()+", "+gridPanel.getRows()+")");
	}


	/**
	 * REFACTORING...
	 */
	private void resized() {
		
		lblCells.setText("Grid("+gridPanel.getCols()+", "+gridPanel.getRows()+")");
		
		int threads = gridPanel.getCols()*gridPanel.getRows();
		Integer[] arrayThreads = new Integer[threads];
		for (int i = 0; i < threads; i++) {
			arrayThreads[i]=i+1;
		}
		threadBox.setModel(new DefaultComboBoxModel<Integer>(arrayThreads));

		repaint();
		
	}
}
