package ar.edu.unlp.CellularAutomaton.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

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

import ar.edu.unlp.CellularAutomaton.model.Alive;
import ar.edu.unlp.CellularAutomaton.model.CellState;
import ar.edu.unlp.CellularAutomaton.model.Dead;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell;
import ar.edu.unlp.CellularAutomaton.model.Neighbor;
import ar.edu.unlp.CellularAutomaton.model.Neighborhood;
import ar.edu.unlp.CellularAutomaton.model.Rule;
import ar.edu.unlp.CellularAutomaton.model.StateRule;
import ar.edu.unlp.CellularAutomaton.model.StateRuleImpl;
import ar.edu.unlp.CellularAutomaton.swing.checkList.JCheckList;
import ar.edu.unlp.CellularAutomaton.swing.checkList.RuleCheckListModel;
import ar.edu.unlp.CellularAutomaton.swing.grid.GameGridModel;
import ar.edu.unlp.CellularAutomaton.swing.grid.JGrid;
import ar.edu.unlp.CellularAutomaton.swing.grid.NeighboorhoodGridModel;









import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Insets;



public class RuleFrame extends JFrame {
	
	public static Rule LIFE = new Rule(	"Life", 
										"Conway's Game of Life", 
										new Neighborhood(new CopyOnWriteArraySet<Neighbor>(Arrays.asList(new Neighbor(-1,-1),new Neighbor(-1,0),new Neighbor(-1,1),new Neighbor(0,-1),new Neighbor(0,1),new Neighbor(1,-1),new Neighbor(1,0),new Neighbor(1,1)))),
										new Alive(new RuleCheckListModel(new StateRuleImpl(8,"Survivals", 2,3))), 
										new Dead(new RuleCheckListModel(new StateRuleImpl(8,"Births", 3))));

	public static Rule HIGHLIFE = new Rule(	"HighLife", 
											"Very similar to Conway's Life but with an interesting replicator", 
											new Neighborhood(new CopyOnWriteArraySet<Neighbor>(Arrays.asList(new Neighbor(-1,-1),new Neighbor(-1,0),new Neighbor(-1,1),new Neighbor(0,-1),new Neighbor(0,1),new Neighbor(1,-1),new Neighbor(1,0),new Neighbor(1,1)))),
											new Alive(new RuleCheckListModel(new StateRuleImpl(8,"Survivals",  2,3))), 
											new Dead(new RuleCheckListModel(new StateRuleImpl(8,"Births", 3,6))));
	
	
	public static Rule DAY_AND_NIGHT = new Rule(	"Day & Night", 
													"Dead cells in a sea of live cells behave the same as live cells in a sea of dead cells", 
													new Neighborhood(new CopyOnWriteArraySet<Neighbor>(Arrays.asList(new Neighbor(-1,-1),new Neighbor(-1,0),new Neighbor(-1,1),new Neighbor(0,-1),new Neighbor(0,1),new Neighbor(1,-1),new Neighbor(1,0),new Neighbor(1,1)))),
													new Alive(new RuleCheckListModel(new StateRuleImpl(8,"Survivals", 3,4,6,7,8))), 
													new Dead(new RuleCheckListModel(new StateRuleImpl(8,"Births", 3,6,7,8)))); 
	
	public static Rule DIAMOEBA = new Rule(	"Diamoeba",
											"Create diamond-shaped blobs with unpredictable behavior", 
											new Neighborhood(new CopyOnWriteArraySet<Neighbor>(Arrays.asList(new Neighbor(-1,-1),new Neighbor(-1,0),new Neighbor(-1,1),new Neighbor(0,-1),new Neighbor(0,1),new Neighbor(1,-1),new Neighbor(1,0),new Neighbor(1,1)))),
											new Alive(new RuleCheckListModel(new StateRuleImpl(8,"Survivals", 5,6,7,8 ))), 
											new Dead(new RuleCheckListModel(new StateRuleImpl(8,"Births", 3,5,6,7,8))));
	
	public static Rule SEEDS = new Rule(	"Seeds", 
											"Every living cell dies every generation, but most patterns still explode", 
											new Neighborhood(new CopyOnWriteArraySet<Neighbor>(Arrays.asList(new Neighbor(-1,-1),new Neighbor(-1,0),new Neighbor(-1,1),new Neighbor(0,-1),new Neighbor(0,1),new Neighbor(1,-1),new Neighbor(1,0),new Neighbor(1,1)))),
											new Alive(new RuleCheckListModel(new StateRuleImpl(8,"Survivals"))), 
											new Dead(new RuleCheckListModel(new StateRuleImpl(8,"Births", 2))));
	
	public static Rule PERSIAN_RUG = new Rule(	"Persian Rug", 
												"A single 2x2 block turns into a set of Persian rugs", 
												new Neighborhood(new CopyOnWriteArraySet<Neighbor>(Arrays.asList(new Neighbor(-1,-1),new Neighbor(-1,0),new Neighbor(-1,1),new Neighbor(0,-1),new Neighbor(0,1),new Neighbor(1,-1),new Neighbor(1,0),new Neighbor(1,1)))),
												new Alive(new RuleCheckListModel(new StateRuleImpl(8,"Survivals"))), 
												new Dead(new RuleCheckListModel(new StateRuleImpl(8,"Births",  2,3,4))));
	
	public static Rule LONGLIFE =new Rule(	"LongLife",
											"Oscillators with extremely long periods can occur quite naturally",  
											new Neighborhood(new CopyOnWriteArraySet<Neighbor>(Arrays.asList(new Neighbor(-1,-1),new Neighbor(-1,0),new Neighbor(-1,1),new Neighbor(0,-1),new Neighbor(0,1),new Neighbor(1,-1),new Neighbor(1,0),new Neighbor(1,1)))),
											new Alive(new RuleCheckListModel(new StateRuleImpl(8,"Survivals", 5))), 
											new Dead(new RuleCheckListModel(new StateRuleImpl(8,"Births", 3,4,5)))); 
	
	public static Rule[] RULES = {LIFE, HIGHLIFE, DAY_AND_NIGHT, DIAMOEBA, SEEDS, PERSIAN_RUG, LONGLIFE};


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable ruleDescriptorTable;
	private JTextArea txtrDesxcription;
	private NeighboorhoodGridModel neighboorhoodGridModel;

	/**
	 * Create the frame.
	 */
	public RuleFrame(final GameGridModel gameGridModel) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameFrame.class.getResource("/ar/edu/unlp/CellularAutomaton/util/icon.png")));
		setTitle("Rules");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 552, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		
		//create StateRuleImpl Panel
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
//		listModels = new ArrayList<RuleCheckListModel>();
//		for (CellState cellState : GameOfLifeCell.STATES) {
//			StateRule rule = cellState.getRule();
//			RuleCheckListModel ruleCheckListModel = new RuleCheckListModel(rule);
//			listModels.add(ruleCheckListModel);
//			JCheckList list = new JCheckList(ruleCheckListModel);
//			ruleCheckListPanel.add(list);
//		}
		
		
		JCheckList aliveStateRuleList = new JCheckList((RuleCheckListModel) gameGridModel.getRule().getAliveState().getStateRule());
		ruleCheckListPanel.add(aliveStateRuleList);
		
		JCheckList deadStateRuleList = new JCheckList((RuleCheckListModel) gameGridModel.getRule().getDeadState().getStateRule());
		ruleCheckListPanel.add(deadStateRuleList);
		
		//create JGrid Panel
		JPanel NeighborhoodComponentPanel = new JPanel();
		NeighborhoodComponentPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Neighboorhood", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
		NeighborhoodComponentPanel.setLayout(new BorderLayout(0, 0));
		contentPane.add(NeighborhoodComponentPanel);
		
		//create JGrid Panel
		neighboorhoodGridModel = new NeighboorhoodGridModel(gameGridModel);
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
				                	Rule rule = RULES[i];
				                	txtrDesxcription.setText(rule.getDescription());
						        	//Update neighboorhoodGridModel
				                	

//				                	gameGridModel.setRule(rule1);
				                	
				                	GameOfLifeCell.ALIVE.getStateRule().loadRule(rule.getAliveState().getStateRule());
						        	GameOfLifeCell.DEAD.getStateRule().loadRule(rule.getDeadState().getStateRule());
						        	gameGridModel.getRule().setNeighborhood(new Neighborhood(new CopyOnWriteArraySet<Neighbor>(Arrays.asList(new Neighbor(-1,-1),new Neighbor(-1,0),new Neighbor(-1,1),new Neighbor(0,-1),new Neighbor(0,1),new Neighbor(1,-1),new Neighbor(1,0),new Neighbor(1,1)))));
						        	
//						        	gameGridModel.setNeighborhood(rule.getNeighborhood());
//						        	gameGridModel.getRule().setNeighborhood(rule.getNeighborhood());
						        	
						        	//Update StateRuleImpl
//						        	GameOfLifeCell.ALIVE.getStateRule().loadRule(rule.getAliveRule());
//						        	GameOfLifeCell.DEAD.getStateRule().loadRule(rule.getDeadRule());
						        	
//						        	listModels.get(0).loadRule(rule.getAliveRule());
//						        	listModels.get(1).loadRule(rule.getDeadRule());
						        	
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
							 value = RULES[rowIndex].getName();
						     break;
						 case 2: 
							 value = RULES[rowIndex].getAliveState().getStateRule().toString()+"/"+RULES[rowIndex].getDeadState().getStateRule();
						     break;
						 case 3: 
							 value = RULES[rowIndex].getDescription();
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
						return RULES.length;
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
