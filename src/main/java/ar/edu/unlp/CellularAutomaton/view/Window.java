package ar.edu.unlp.CellularAutomaton.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Window {

	private JFrame frmGameOfLife;
	private GridPanel gridPanel;
	private JLabel lblNumGenerations;
	private JButton btnNext;
	private JButton btnStart;
	private JComboBox comboBox;

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
		this.frmGameOfLife.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGameOfLife = new JFrame();
		frmGameOfLife.setTitle("Game Of Life");
		frmGameOfLife.setBounds(100, 100, 450, 300);
		frmGameOfLife.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		frmGameOfLife.getContentPane().add(panel, BorderLayout.SOUTH);

		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gridPanel.next();
				lblNumGenerations.setText(gridPanel.getGeneration());
			}
		});
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnStart.getText() == "Start"){
					btnNext.setEnabled(false);
					btnStart.setText("Stop");
					for (int i = 0; i < 5; i++) {
						gridPanel.next();
						lblNumGenerations.setText(gridPanel.getGeneration());
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				} else{
					btnNext.setEnabled(true);
					btnStart.setText("Start");
				}

			}
		});
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"clear", "otro", "otro", "otro"}));
//		panel.add(comboBox);
//		panel.add(btnStart);
		panel.add(btnNext);
		
		JLabel lblGenerations = new JLabel("Generations:");
		panel.add(lblGenerations);
		
		lblNumGenerations = new JLabel("0");
		panel.add(lblNumGenerations);

		gridPanel = new GridPanel(15,25);
		frmGameOfLife.getContentPane().add(gridPanel, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		frmGameOfLife.setJMenuBar(menuBar);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frmGameOfLife, "Game Of Life - Carmona Marcelo - 2014");
			}
		});
		mnHelp.add(mntmAbout);
	}


}
