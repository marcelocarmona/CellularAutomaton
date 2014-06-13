package ar.edu.unlp.CellularAutomaton.swing.checkList;

import java.util.List;

import javax.swing.AbstractListModel;

import ar.edu.unlp.CellularAutomaton.model.RuleValue;
import ar.edu.unlp.CellularAutomaton.model.StateRule;

/**
 * Model
 * @author mclo
 */
public class RuleCheckListModel extends AbstractListModel<RuleValue> implements StateRule{

	private static final long serialVersionUID = 1L;
	private StateRule rule;
	
	public RuleCheckListModel(StateRule rule) {
		super();
		this.rule = rule;
	}
	
	public StateRule getRule() {
		return rule;
	}

	public void setRule(StateRule rule) {
		this.rule = rule;
		fireContentsChanged(this, 0, rule.size());
	}

	public String getName(){
		return rule.getName();
	}

	public int getSize() {
		return rule.size();
	}

	public RuleValue getElementAt(int index) {
		return rule.get(index);
	}

    /**
     * Adds the specified component to the end of this list.
	 * @param ruleValue the component to be added
	 */
	public void addLast() {
		int index = rule.size();
		rule.addLast();
		fireIntervalAdded(this, index, index);
	}

	/**
	 * Remove the last element
	 */
	public void removeLast() {
		int index = rule.size();
		rule.removeLast();
        fireIntervalRemoved(this, index, index);
	}

	public void loadRule(StateRule rule){
		this.rule.loadRule(rule);
		fireContentsChanged(this, 0, rule.size());
	}

	@Override
	public void setName(String name) {
		this.rule.setName(name);
	}

	@Override
	public List<RuleValue> getValues() {
		return this.rule.getValues();
	}

	@Override
	public void add(RuleValue ruleValue) {
		this.rule.add(ruleValue);
	}

	@Override
	public int size() {
		return this.rule.size();
	}

	@Override
	public RuleValue get(int index) {
		return this.rule.get(index);
	}

	@Override
	public void setNeighborsCounter(int numberOfNeighbor, boolean b) {
		this.rule.setNeighborsCounter(numberOfNeighbor, b);
	}

	@Override
	public boolean include(int numberOfNeighbor) {
		return this.rule.include(numberOfNeighbor);
	}

	
};