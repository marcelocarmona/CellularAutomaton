package ar.edu.unlp.CellularAutomaton.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.BevelBorder;

import ar.edu.unlp.CellularAutomaton.model.CellState;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell;
import ar.edu.unlp.CellularAutomaton.util.Rule;

/**
 * Frame to change rules
 * @author mclo
 */
public class RuleFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JCheckBox aliveBoxes[] = new JCheckBox[9];
	private JCheckBox deadBoxes[] = new JCheckBox[9];

	/**
	 * Create the frame.
	 */
	public RuleFrame() {
		setTitle("Rules");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 726, 138);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		final JComboBox<Rule> comboBox = new JComboBox<Rule>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rule rule = (Rule) comboBox.getSelectedItem();
				GameOfLifeCell.ALIVE.setArrayRule(rule.getAliveRule());
				GameOfLifeCell.DEAD.setArrayRule(rule.getDeadRule());
				
				for (int i = 0; i < 9; i++) {
					aliveBoxes[i].setSelected(GameOfLifeCell.ALIVE.getArrayRule().getNeighborsCounter(i));
					deadBoxes[i].setSelected(GameOfLifeCell.DEAD.getArrayRule().getNeighborsCounter(i));
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<Rule>(Rule.values()));
		panel_1.add(comboBox);

		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 9, 0, 0));
		
		for (int i = 0; i < 9; i++) {
			final JCheckBox aliveBox  = aliveBoxes[i]  = new JCheckBox(String.valueOf(i));
			aliveBox.addActionListener(new ActionBox(i,GameOfLifeCell.ALIVE,aliveBox));
			if (GameOfLifeCell.ALIVE.getArrayRule().getNeighborsCounter(i))
					aliveBox.setSelected(true);
			panel.add(aliveBox);
		}
		
		for (int i = 0; i < 9; i++) {
			final JCheckBox deadBox = deadBoxes[i] = new JCheckBox(String.valueOf(i));
			deadBox.addActionListener(new ActionBox(i,GameOfLifeCell.DEAD,deadBox));
			if (GameOfLifeCell.DEAD.getArrayRule().getNeighborsCounter(i))
				deadBox.setSelected(true);
			panel.add(deadBox);
		}
	}
	
	private class ActionBox implements ActionListener{
		int numberOfNeighbors;
		CellState cellState;
		JCheckBox jCheckBox;

		public ActionBox(int numberOfNeighbors, CellState cellState, JCheckBox jCheckBox) {
			super();
			this.numberOfNeighbors = numberOfNeighbors;
			this.cellState = cellState;
			this.jCheckBox = jCheckBox;
		}

		public void actionPerformed(ActionEvent e) {
		    if (jCheckBox.isSelected())
		    	cellState.getArrayRule().setNeighborsCounter(numberOfNeighbors,true);
		      else
		    	cellState.getArrayRule().setNeighborsCounter(numberOfNeighbors,false);
		}
	}

}
