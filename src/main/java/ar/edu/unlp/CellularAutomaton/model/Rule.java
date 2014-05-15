package ar.edu.unlp.CellularAutomaton.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Rule used to change state of the cells
 * @see Alive
 * @see Dead
 * @author mclo
 */
public class Rule {
	/**
	 * Name of the Rule
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
	public Rule(int size,String name,int... numOfNeighbors) {
		this.name = name;

		for (int i = 0; i < size + 1; i++) {
			values.add(new RuleValue(i, false));
		}
		
		for (int i : numOfNeighbors) {
			setNeighborsCounter(i,true);
		}

	}

	/**
	 * @return name of rule
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name name of rule
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return RuleValues
	 */
	public List<RuleValue> getValues() {
		return values;
	}

	/**
	 * @param items
	 */
	public void setItems(List<RuleValue> items) {
		//TODO arreglar esto
		this.values = items;
	}
	
	/**
	 * Copies the ruleValues of the rule parameter and load it in the current rule
	 * @param rule a Rule
	 */
	public void loadRule(Rule rule){
		values.clear();
		for (RuleValue ruleValue : rule.getValues()) {
			values.add(new RuleValue(ruleValue.getIdCount(), ruleValue.isSelected()));
		}
	}
	
	public void add(RuleValue ruleValue){
		values.add(ruleValue);
	}

	public int size(){
		return values.size();
	}
	
	public RuleValue get(int index){
		return values.get(index);
	}

	/**
	 * Add a RuleValue to the end
	 */
	public void addLast() {
		values.add(new RuleValue(values.size(), false));
	}

	/**
	 * Remove the last Rulevalue
	 */
	public void remove() {
		values.remove(values.size()-1);
	}
	
	/**
	 * @param numberOfNeighbors position of the array
	 * @param value set value
	 */
	public void setNeighborsCounter(int numberOfNeighbor, boolean b) {
		values.get(numberOfNeighbor).setSelected(b);
	}

	/**
	 * @param numOfNeighbors
	 * @return if numOfNeighbors is include in the array
	 */
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
