package ar.edu.unlp.CellularAutomaton.swing.checkList;

import javax.swing.AbstractListModel;

import ar.edu.unlp.CellularAutomaton.model.Rule;
import ar.edu.unlp.CellularAutomaton.model.RuleValue;

/**
 * Model
 * @author mclo
 */
public class RuleCheckListModel extends AbstractListModel<RuleValue> {

	private static final long serialVersionUID = 1L;
	private Rule rule;
	
	public RuleCheckListModel(Rule rule) {
		super();
		this.rule = rule;
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
	public void addElement() {
		int index = rule.size();
		rule.addLast();
		fireIntervalAdded(this, index, index);
	}

	/**
	 * Remove the last element
	 */
	public void removeElement() {
		int index = rule.size();
		rule.remove();
        fireIntervalRemoved(this, index, index);
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
		fireContentsChanged(this, 0, rule.size());
	}
	
	public void loadRule(Rule rule){
		this.rule.loadRule(rule);
		fireContentsChanged(this, 0, rule.size());
	}
	
	
	
};