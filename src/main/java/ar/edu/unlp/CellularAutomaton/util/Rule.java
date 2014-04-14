package ar.edu.unlp.CellularAutomaton.util;

import ar.edu.unlp.CellularAutomaton.model.ArrayRule;

/**
 * Rule selected in the RuleFrame
 * @see RuleFrame
 * @author mclo
 */
public enum Rule {
	LIFE("Life", "Conway's Game of Life", new int[]{3}, new int[]{2,3}),
	HIGHLIFE("HighLife", "Very similar to Conway's Life but with an interesting replicator", new int[]{3,6}, new int[]{2,3}),
	DAY_AND_NIGHT("Day & Night", "Dead cells in a sea of live cells behave the same as live cells in a sea of dead cells", new int[]{3,6,7,8}, new int[]{3,4,6,7,8}),
	DIAMOEBA("Diamoeba","Create diamond-shaped blobs with unpredictable behavior", new int[]{3,5,6,7,8}, new int[]{5,6,7,8}),
	SEEDS("Seeds", "Every living cell dies every generation, but most patterns still explode", new int[]{2}, new int[]{}),
	PERSIAN_RUG("Persian Rug", "A single 2x2 block turns into a set of Persian rugs", new int[]{2,3,4}, new int[]{}),
	LONGLIFE("LongLife","Oscillators with extremely long periods can occur quite naturally", new int[]{3,4,5},new int[]{5});
	
	private final String name;
	private final String description;
	private int[] aliveArray;
	private int[] deadArray;
	
	/**
	 * @param name rule's name
	 * @param description rule's description
	 * @param deadArray to create an ArrayRule for dead state
	 * @param aliveArray to create an ArrayRule for dead alive
	 */
	private Rule(String name, String description,int[] deadArray, int[] aliveArray) {
		this.name = name;
		this.description = description;
		this.aliveArray = aliveArray;
		this.deadArray = deadArray;
	}
	
    /**
     * @return an ArrayRule
     */
    public ArrayRule getAliveRule() {
		return new ArrayRule(aliveArray);
	}

	/**
	 * @return an ArrayRule
	 */
	public ArrayRule getDeadRule() {
		return new ArrayRule(deadArray);
	}

	/**
	 * @return rule's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return rule's description
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    public String toString() {
		StringBuilder b = new StringBuilder();
		for (int number : aliveArray) {
			b.append(number);
		}
		b.append("/");
		for (int number : deadArray) {
			b.append(number);
		}
		b.append(" - ");
		b.append(name);
		b.append(" - ");
		b.append(description);
		return b.toString();
      }
}
