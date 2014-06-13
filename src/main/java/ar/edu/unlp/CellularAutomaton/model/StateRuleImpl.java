package ar.edu.unlp.CellularAutomaton.model;

import java.util.ArrayList;
import java.util.List;

/**
 * StateRuleImpl used to change state of the cells
 * @see Alive
 * @see Dead
 * @author mclo
 */
public class StateRuleImpl implements StateRule {
	/**
	 * Name of the StateRuleImpl
	 */
	private String name;
	
	/**
	 * values
	 * [0,1,2,3,4,5,6,7,8]
	 * 	| | | | | | | | |
	 *  | | | | | | | | +->RuleValue to represent eight neighbors cells
	 *  | | | | | | | +->RuleValue to represent seven neighbors cells
	 *  | | | | | | +->RuleValue to represent six neighbors cells
	 *  | | | | | +->RuleValue to represent five neighbors cells
	 *  | | | | +->RuleValue to represent four neighbors cells
	 *  | | | +->RuleValue to represent three neighbors cells
	 *  | | +->RuleValue to represent two neighbors cells
	 *  | +->RuleValue to represent one neighbors cells
	 *  +->RuleValue to represent zero neighbors cells
	 */
	private List<RuleValue> values = new ArrayList<RuleValue>();
	
	/**
	 * Initialize the neighborsCounter to false and put true the values of numOfNeighbors
	 * @param name Name of rule
	 * @param neighborhood set of neighbors
	 * @param numOfNeighbors numbers that in the list of RuleValue will be selected
	 */
	public StateRuleImpl(int size,String name,int... numOfNeighbors) {
		this.name = name;

		for (int i = 0; i < size + 1; i++) {
			values.add(new RuleValue(i, false));
		}
		
		for (int i : numOfNeighbors) {
			setNeighborsCounter(i,true);
		}

	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.StateRule#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.StateRule#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.StateRule#getValues()
	 */
	@Override
	public List<RuleValue> getValues() {
		return values;
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.StateRule#loadRule(ar.edu.unlp.CellularAutomaton.model.StateRule)
	 */
	@Override
	public void loadRule(StateRule rule){
		values.clear();
		for (RuleValue ruleValue : rule.getValues()) {
			values.add(new RuleValue(ruleValue.getIdCount(), ruleValue.isSelected()));
		}
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.StateRule#add(ar.edu.unlp.CellularAutomaton.model.RuleValue)
	 */
	@Override
	public void add(RuleValue ruleValue){
		values.add(ruleValue);
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.StateRule#size()
	 */
	@Override
	public int size(){
		return values.size();
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.StateRule#get(int)
	 */
	@Override
	public RuleValue get(int index){
		return values.get(index);
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.StateRule#addLast()
	 */
	@Override
	public void addLast() {
		values.add(new RuleValue(values.size(), false));
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.StateRule#remove()
	 */
	@Override
	public void removeLast() {
		values.remove(values.size()-1);
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.StateRule#setNeighborsCounter(int, boolean)
	 */
	@Override
	public void setNeighborsCounter(int numberOfNeighbor, boolean b) {
		values.get(numberOfNeighbor).setSelected(b);
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.StateRule#include(int)
	 */
	@Override
	public boolean include(int numberOfNeighbor){
		return values.get(numberOfNeighbor).isSelected();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String name = "";
		for (int i = 0; i < size(); i++) {
			if (values.get(i).isSelected() == true) 
				name=name+i;
		}
		return name;
	}

}
