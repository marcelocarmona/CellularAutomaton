package ar.edu.unlp.CellularAutomaton.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import ar.edu.unlp.CellularAutomaton.util.SpeedTime;


import ar.edu.unlp.CellularAutomaton.util.CellSize;
import ar.edu.unlp.CellularAutomaton.util.StartThread;

import java.awt.Color;


/**
 * Main window contains a gridPanel
 * @author mclo
 */
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
	private JPanel bottomPanel;
	

	private StartThread managerOfThreads;

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
		
		
		//gridPanel
		gridPanel = new GridPanel(20,20,20);
		gridPanel.addGridPanelListener(new GridPanelListener() {
			
			@Override
			public void sizeChanged(GridPanelEvent gridPanelEvent) {
				
				lblCells.setText("Grid("+gridPanelEvent.getCols()+", "+gridPanelEvent.getRows()+")");
			
				
			}
		});
		contentPane.add(gridPanel, BorderLayout.CENTER);	

		//bottomPanel
		bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(255, 250, 205));
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		
		lblCells = new JLabel();
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
				} else {
					tglbtnStart.setText("Start");
					stopManagerOfThreads();
					newManagerOfThreads();
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
		managerOfThreads = new StartThread(this,getSpeedTime());
	}
	
	/**
	 * Start Thread
	 */
	private void startManagerOfThreads(){
		managerOfThreads.start();
	}
	
	/**
	 * Stop Thread
	 */
	private void stopManagerOfThreads(){
		managerOfThreads.finish();
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
	


}
