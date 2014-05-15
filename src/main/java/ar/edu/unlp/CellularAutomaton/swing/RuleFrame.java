package ar.edu.unlp.CellularAutomaton.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import ar.edu.unlp.CellularAutomaton.model.CellState;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeGrid;
import ar.edu.unlp.CellularAutomaton.model.Rule;
import ar.edu.unlp.CellularAutomaton.model.RuleDescriptor;
import ar.edu.unlp.CellularAutomaton.swing.checkList.JCheckList;
import ar.edu.unlp.CellularAutomaton.swing.checkList.RuleCheckListModel;
import ar.edu.unlp.CellularAutomaton.swing.grid.GameGridModel;
import ar.edu.unlp.CellularAutomaton.swing.grid.GridListener;
import ar.edu.unlp.CellularAutomaton.swing.grid.JGrid;
import ar.edu.unlp.CellularAutomaton.swing.grid.NeighboorhoodGridModel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Insets;



public class RuleFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable ruleDescriptorTable;
	private JTextArea txtrDesxcription;
	private List<RuleCheckListModel> listModels;
	private NeighboorhoodGridModel neighboorhoodGridModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RuleFrame frame = new RuleFrame(new GameGridModel(new GameOfLifeGrid(5, 5)));
					frame.setVisible(true);
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RuleFrame(final GameGridModel gameGridModel) {
		setTitle("Rules");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 552, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		
		//create Rule Panel
		JPanel rulePanel = new JPanel();
		rulePanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Rules", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
		rulePanel.setLayout(new BoxLayout(rulePanel, BoxLayout.Y_AXIS));
		contentPane.add(rulePanel);
		
		JScrollPane ruleScrollPane = new JScrollPane();
		ruleScrollPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		rulePanel.add(ruleScrollPane);
		
		//create RuleCheckListPanel
		JPanel ruleCheckListPanel = new JPanel();
		ruleCheckListPanel.setBorder(null);
		ruleScrollPane.setViewportView(ruleCheckListPanel);
		
		
		//initialize lists models and NeighborhoodComponent
		listModels = new ArrayList<RuleCheckListModel>();
		for (CellState cellState : GameOfLifeCell.STATES) {
			Rule rule = cellState.getRule();
			RuleCheckListModel ruleCheckListModel = new RuleCheckListModel(rule);
			listModels.add(ruleCheckListModel);
			JCheckList list = new JCheckList(ruleCheckListModel);
			ruleCheckListPanel.add(list);
		}
		
		//create JGrid Panel
		JPanel NeighborhoodComponentPanel = new JPanel();
		NeighborhoodComponentPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Neighboorhood", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
		NeighborhoodComponentPanel.setLayout(new BorderLayout(0, 0));
		contentPane.add(NeighborhoodComponentPanel);
		
		//create JGrid Panel
		neighboorhoodGridModel = new NeighboorhoodGridModel(listModels,gameGridModel);
		JGrid jGrid = new JGrid(neighboorhoodGridModel);


		
		NeighborhoodComponentPanel.add(jGrid);
		


		
		JPanel ruleDescriptorPanel = new JPanel();
		ruleDescriptorPanel.setBorder(new TitledBorder(null, "Default Rules", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		rulePanel.add(ruleDescriptorPanel);
		ruleDescriptorPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel ruleTableDescriptorPanel = new JPanel();
		ruleDescriptorPanel.add(ruleTableDescriptorPanel);
				ruleTableDescriptorPanel.setLayout(new BoxLayout(ruleTableDescriptorPanel, BoxLayout.X_AXIS));
	

				
				JScrollPane ruleDescriptorScrollPane = new JScrollPane();
				ruleDescriptorScrollPane.setBorder(null);
				ruleTableDescriptorPanel.add(ruleDescriptorScrollPane);
				ruleDescriptorScrollPane.getViewport().setName("jajja");
				
				gameGridModel.addGridListener(new GridListener() {
					
					@Override
					public void stateChanged(ChangeEvent e) {
						System.out.println("cambio");
						
					}
					
					@Override
					public void cellSizeChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				
				//TODO accion de la tabla al hacer click
				ruleDescriptorTable = new JTable();
				ruleDescriptorTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						//Update Description
						ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				        if (!lsm.isSelectionEmpty()) {
				            // Find out which indexes are selected.
				            int minIndex = lsm.getMinSelectionIndex();
				            int maxIndex = lsm.getMaxSelectionIndex();
				            for (int i = minIndex; i <= maxIndex; i++) {
				                if (lsm.isSelectedIndex(i)) {
				                	RuleDescriptor ruleDescriptor = RuleDescriptor.values()[i];
				                	txtrDesxcription.setText(ruleDescriptor.getDescription());
						        	//Update neighboorhoodGridModel
						        	System.out.println(gameGridModel.getNeighborhood());
						        	gameGridModel.setNeighborhood(ruleDescriptor.getNeighborhood());
						        	//Update Rule
						        	listModels.get(0).loadRule(ruleDescriptor.getAliveRule());
						        	listModels.get(1).loadRule(ruleDescriptor.getDeadRule());
//						        	GameOfLifeCell.ALIVE.setRule(ruleDescriptor.getAliveRule());
//						        	GameOfLifeCell.DEAD.setRule(ruleDescriptor.getDeadRule());
						        	//TODO ARREGLAR

				                }
				            }
				        }
				        

						
					}
				});
				ruleDescriptorTable.setPreferredScrollableViewportSize(new Dimension(200, 50));
				ruleDescriptorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				ruleDescriptorScrollPane.setViewportView(ruleDescriptorTable);
				ruleDescriptorTable.setModel(new AbstractTableModel() {
					
					private static final long serialVersionUID = 1L;
					
					@Override
					public Object getValueAt(int rowIndex, int columnIndex) {
						Object value = null;
						switch(columnIndex) {
						 case 0: 
							 value = rowIndex;
						     break;
						 case 1: 
							 value = RuleDescriptor.values()[rowIndex].getName();
						     break;
						 case 2: 
							 value = RuleDescriptor.values()[rowIndex].getAliveRule().toString()+"/"+RuleDescriptor.values()[rowIndex].getDeadRule();
						     break;
						 case 3: 
							 value = RuleDescriptor.values()[rowIndex].getDescription();
						}
						return value;
						
					}
					
					@Override
					public String getColumnName(int column) {
						String value = null;
						switch(column) {
						 case 0: 
							 value = "#";
						     break;
						 case 1: 
							 value = "Name";
						     break;
						 case 2: 
							 value = "Definition";
						     break;
						 case 3: 
							 value = "Description";
						}
						return value;
					}

					@Override
					public int getRowCount() {
						return RuleDescriptor.values().length;
					}
					
					@Override
					public int getColumnCount() {
						return 3;
					}
				});
				
				ruleDescriptorTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
				ruleDescriptorTable.getColumnModel().getColumn(0).setMaxWidth(20);
				ruleDescriptorTable.getColumnModel().getColumn(1).setMaxWidth(200);
				ruleDescriptorTable.getColumnModel().getColumn(2).setMaxWidth(550);

				
				
				JPanel descriptionPanel = new JPanel();
				rulePanel.add(descriptionPanel);
				descriptionPanel.setBorder(new TitledBorder(null, "Description", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.X_AXIS));
				
				JScrollPane descriptionScrollPane = new JScrollPane();
				descriptionPanel.add(descriptionScrollPane);
				
				txtrDesxcription = new JTextArea(2,5);
				txtrDesxcription.setMargin(new Insets(5, 5, 5, 5));
				txtrDesxcription.setWrapStyleWord(true);
				txtrDesxcription.setLineWrap(true);
				txtrDesxcription.setEditable(false);
				descriptionScrollPane.setViewportView(txtrDesxcription);

	}

}
