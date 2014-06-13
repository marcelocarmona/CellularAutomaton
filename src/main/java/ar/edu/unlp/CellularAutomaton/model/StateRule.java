package ar.edu.unlp.CellularAutomaton.model;

import java.util.List;

public interface StateRule {

	/**
	 * @return name of rule
	 */
	public abstract String getName();

	/**
	 * @param name name of rule
	 */
	public abstract void setName(String name);

	/**
	 * @return RuleValues
	 */
	public abstract List<RuleValue> getValues();

	/**
	 * Copies the ruleValues of the rule parameter and load it in the current rule
	 * @param rule a StateRuleImpl
	 */
	public abstract void loadRule(StateRule rule);

	public abstract void add(RuleValue ruleValue);

	public abstract int size();

	public abstract RuleValue get(int index);

	/**
	 * Add a RuleValue to the end
	 */
	public abstract void addLast();

	/**
	 * Remove the last Rulevalue
	 */
	public abstract void removeLast();

	/**
	 * @param numberOfNeighbors position of the array
	 * @param value set value
	 */
	public abstract void setNeighborsCounter(int numberOfNeighbor, boolean b);

	/**
	 * @param numOfNeighbors
	 * @return if numOfNeighbors is include in the array
	 */
	public abstract boolean include(int numberOfNeighbor);

}