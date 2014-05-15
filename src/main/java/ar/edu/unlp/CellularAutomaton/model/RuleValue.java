package ar.edu.unlp.CellularAutomaton.model;

public class RuleValue {
	private int idCount;
	private boolean selected;

	public RuleValue(int idCount, boolean selected) {
		super();
		this.idCount = idCount;
		this.selected = selected;
	}

	public int getIdCount() {
		return idCount;
	}

	public void setIdCount(int idCount) {
		this.idCount = idCount;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public String toString() {
		return String.valueOf(idCount);
//		return getClass().getName()+" [ idCount="+idCount+", selected="+ Boolean.toString(selected)+"]";
	}

}
